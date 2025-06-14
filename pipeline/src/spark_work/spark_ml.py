from pyspark.sql import SparkSession, DataFrame, Row
from pyspark.sql.functions import udf, col, date_format
from pyspark.sql.types import ArrayType, StringType, FloatType
import utils
import re
from nltk.corpus import stopwords
import datetime
import nltk


print("🔄 Baixando stopwords do NLTK...")
nltk.download('stopwords')
print("✅ Stopwords baixadas.")


def create_spark_session() -> SparkSession:
    print("🔄 Criando SparkSession...")
    spark = SparkSession.builder \
        .appName("Recommendation Spark") \
        .config("spark.jars.packages", ",".join([
            "org.postgresql:postgresql:42.6.0",
            "org.apache.spark:spark-sql-kafka-0-10_2.12:3.5.0",
            "org.elasticsearch:elasticsearch-spark-30_2.12:8.13.4",
        ])) \
        .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    spark.sparkContext.addPyFile("/work/utils.py")
    print("✅ SparkSession criada.")
    return spark


def get_stopwords_broadcast(spark):
    print("🔄 Broadcast das stopwords...")
    stopwords_pt = set(stopwords.words('portuguese'))
    bc = spark.sparkContext.broadcast(stopwords_pt)
    print("✅ Broadcast das stopwords pronto.")
    return bc


def tokenize_factory(bc_stopwords):
    print("🔄 Criando função de tokenização...")
    def tokenize(text):
        tokens = re.findall(r'\b\w+\b', text.lower())
        return [t for t in tokens if t not in bc_stopwords.value]
    print("✅ Função de tokenização criada.")
    return tokenize


def jaccard(a, b):
    set_a, set_b = set(a), set(b)
    inter = len(set_a & set_b)
    union = len(set_a | set_b)
    return float(inter) / union if union else 0.0


def compute_similarity_matrix(df, jaccard_udf, similarity_threshold):
    print("🔄 Calculando matriz de similaridade...")
    df_cross = df.alias("a").crossJoin(df.alias("b")) \
        .withColumn("sim", jaccard_udf(col("a.tokens"), col("b.tokens")))
    df_sim = df_cross.filter(col("a.id") != col("b.id")) \
        .filter(col("sim") >= similarity_threshold) \
        .select(col("a.id").alias("centroid_id"), col("b.id").alias("neighbor_id"), "sim")
    print("✅ Matriz de similaridade calculada.")
    return df_sim


def butina_clustering(df, df_sim):
    print("🔄 Iniciando clustering Butina...")
    df_neighbors = df_sim.groupBy("centroid_id").count().withColumnRenamed("count", "num_neighbors")
    df_sorted = df_neighbors.orderBy(col("num_neighbors").desc())
    ids_sorted = [row.centroid_id for row in df_sorted.collect()]
    flagged = set()
    clusters = {}
    cluster_id = 0
    for idx in ids_sorted:
        if idx in flagged:
            continue
        cluster_id += 1
        centroid_row = df.filter(col("id") == idx).collect()[0]
        clusters[cluster_id] = {"centroid_id": idx, "centroid_synopsis": centroid_row.sinopse, "members": [], "member_ids": []}
        flagged.add(idx)
        neighbors = df_sim.filter(col("centroid_id") == idx).select("neighbor_id").rdd.flatMap(lambda x: x).collect()
        for n in neighbors:
            if n not in flagged:
                member_row = df.filter(col("id") == n).collect()[0]
                clusters[cluster_id]["members"].append(member_row.sinopse)
                clusters[cluster_id]["member_ids"].append(n)
                flagged.add(n)
    all_ids = set(df.select("id").rdd.flatMap(lambda x: x).collect())
    singletons = [df.filter(col("id") == i).collect()[0].sinopse for i in (all_ids - flagged)]
    print(f"✅ Clustering Butina finalizado. {len(clusters)} clusters encontrados.")
    return clusters, singletons


def recomend_by_synopsis(synopsis_user, clusters, tokenize, jaccard, threshold=0.1):
    set_user = set(tokenize(synopsis_user))
    best_cluster = None
    best_sim = 0
    for cid, data in clusters.items():
        set_centroid = set(tokenize(data['centroid_synopsis']))
        sim = jaccard(set_user, set_centroid)
        if sim > best_sim and sim >= threshold:
            best_sim = sim
            best_cluster = cid
    if best_cluster is not None:
        recommendations = [(clusters[best_cluster]['centroid_id'], clusters[best_cluster]['centroid_synopsis'])] + \
                        list(zip(clusters[best_cluster]['member_ids'], clusters[best_cluster]['members']))
        print(f"✅ Recomendações geradas para o cluster {best_cluster}.")
        return recommendations
    else:
        # print("⚠️ Nenhuma recomendação encontrada para a sinopse do usuário.")
        return []


def process_batch_with_recommendations(df, batch_id, clusters, tokenize, jaccard, df_books_metadata):
    print(f"🔄 Processando batch {batch_id}...")
    if df.count() == 0:
        print(f"⚠️ Batch {batch_id} vazio. Pulando.")
        return
    df_read_end = df.filter(col("interactionType") == "read_end")
    if df_read_end.count() == 0:
        print(f"⚠️ Nenhum evento 'read_end' no batch {batch_id}. Pulando.")
        return
    user_books = df_read_end.select("userId", "bookId", "synopsis", "timestamp").collect()
    recs = []
    for row in user_books:
        user_id = row["userId"]
        book_id = row["bookId"]
        user_synopsis = row["synopsis"]
        timestamp = row["timestamp"]
        if user_synopsis is None:
            book_row = df_books_metadata.filter(col("id") == book_id).select("synopsis").collect()
            if book_row and book_row[0]["synopsis"]:
                user_synopsis = book_row[0]["synopsis"]
            else:
                print(f"⚠️ Livro {book_id} sem sinopse. Pulando recomendação para usuário {user_id}.")
                continue
        recommendations = recomend_by_synopsis(user_synopsis, clusters, tokenize, jaccard, threshold=0.05)
        recommended_book_ids = [str(bid) for bid, _ in recommendations if bid != book_id]
        if recommended_book_ids:
            recs.append({
                "book_id": book_id,
                "user_id": user_id,
                "recommended_book_ids": recommended_book_ids,
                "timestamp": timestamp if timestamp else datetime.datetime.utcnow().isoformat()
            })
    if not recs:
        print(f"⚠️ Nenhuma recomendação gerada no batch {batch_id}.")
        return
    spark = SparkSession.getActiveSession()
    recs_spark_df = spark.createDataFrame(recs)
    recs_spark_df = recs_spark_df.withColumn("timestamp", date_format(col("timestamp"), utils.USO_UTC_8601_DATE_FORMAT))
    utils.write_to_elasticsearch(recs_spark_df, "user-recommendations", "append")
    print(f"✅ {len(recs)} recomendações escritas no índice Elasticsearch user-recommendations.")

def write_stream_with_recommendations(df: DataFrame, clusters, tokenize, jaccard, df_books_metadata) -> None:
    print("🔄 Iniciando streaming de recomendações...")
    def foreach_batch_function(batch_df, batch_id):
        # print(f"Batch {batch_id} preview:")
        # batch_df.show(truncate=True)
        process_batch_with_recommendations(batch_df, batch_id, clusters, tokenize, jaccard, df_books_metadata)
    query = df.writeStream \
        .outputMode("append") \
        .foreachBatch(foreach_batch_function) \
        .start()
    print("✅ Streaming de recomendações iniciado. Aguardando término...")
    query.awaitTermination()


def process_clusters(df_books_metadata, spark):
    print("🔄 Preparando dados para clustering...")
    synopses_rows = df_books_metadata.select("id", "synopsis").where(col("synopsis").isNotNull()).collect()
    ids = [row["id"] for row in synopses_rows]
    synopses = [row["synopsis"] for row in synopses_rows]
    bc_stopwords = get_stopwords_broadcast(spark)
    tokenize = tokenize_factory(bc_stopwords)
    df_synopses = spark.createDataFrame([Row(id=i, sinopse=s) for i, s in zip(ids, synopses)])
    tokenize_udf = udf(tokenize, ArrayType(StringType()))
    df_synopses = df_synopses.withColumn("tokens", tokenize_udf(col("sinopse")))
    jaccard_udf = udf(jaccard, FloatType())
    similarity_threshold = 0.05
    df_sim = compute_similarity_matrix(df_synopses, jaccard_udf, similarity_threshold)
    clusters, _ = butina_clustering(df_synopses, df_sim)
    utils.save_clusters(clusters, utils.BUTINA_RECOMENTADTIONS_CLUSTERS_PATH)
    print("✅ Dados de clustering prontos.")
    return clusters, tokenize, jaccard


def get_clusters(df_books_metadata, spark):
    clusters = utils.load_clusters(utils.BUTINA_RECOMENTADTIONS_CLUSTERS_PATH)
    if clusters is not None:
        print("✅ Clusters carregados do disco.")
        bc_stopwords = get_stopwords_broadcast(spark)
        tokenize = tokenize_factory(bc_stopwords)
        return clusters, tokenize, jaccard
    else:
        print("🔄 Clusters não encontrados. Calculando...")
        return process_clusters(df_books_metadata, spark)


def main():
    print("🚀 Iniciando pipeline de recomendações...")
    spark = create_spark_session()
    print("🔄 Garantindo índice no Elasticsearch...")
    utils.ensure_es_index(utils.ELASTIC_USER_RECOMMENDATIONS_INDEX_NAME, utils.USER_RECOMMENDATIONS_INDEX_MAPPING)
    print("🔄 Lendo metadados dos livros do PostgreSQL...")
    df_books_metadata = utils.read_from_postgres(spark, "books_metadata")
    clusters, tokenize, jaccard = get_clusters(df_books_metadata, spark)
    df_kafka_raw = utils.read_kafka_stream(spark, utils.KAFKA_USERS_INTERACTION_TOPIC_NAME)
    df_kafka_json = utils.parse_kafka_json(df_kafka_raw, utils.KAFKA_USERS_INTERACTION_SCHEMA)
    df_kafka_postgres = utils.join_interaction_to_book_metadata(df_kafka_json, df_books_metadata)
    print("✅ Iniciando processamento de recomendações...")
    write_stream_with_recommendations(df_kafka_postgres, clusters, tokenize, jaccard, df_books_metadata)

if __name__ == "__main__":
    main()