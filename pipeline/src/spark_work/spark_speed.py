from pyspark.sql import SparkSession, DataFrame
from pyspark.sql.streaming import StreamingQuery
from pyspark.sql.functions import col, lit, date_format

import utils

ENABLE_CONSOLE_MODE = False

def create_spark_session() -> SparkSession:
    print("🚀 Starting Spark Session setup...")
    spark = SparkSession.builder \
        .appName("Speed Spark") \
        .config("spark.jars.packages", ",".join( [
            "org.postgresql:postgresql:42.6.0",
            "org.apache.spark:spark-sql-kafka-0-10_2.12:3.5.0",
            "org.elasticsearch:elasticsearch-spark-30_2.12:8.13.4",
        ])) \
        .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    spark.sparkContext.addPyFile("/work/utils.py")
    print("✅ Spark Session started.")
    return spark

def write_interactions_to_console(df: DataFrame, batch_id: int) -> None:
    if df.count() == 0:
        return
    print(f"🖨️ Writing batch {df} to console...")
    df.withColumn("batch_id", lit(batch_id)).show(truncate=True)


def write_stream_to_console(df: DataFrame) -> StreamingQuery:
    query = df \
        .writeStream \
        .foreachBatch(write_interactions_to_console) \
        .outputMode("append") \
        .start()
    print("✅ Streaming started. Awaiting termination...")
    query.awaitTermination()
    return query


def write_interactions_to_elasticsearch(df: DataFrame, batch_id: int) -> None:
    if df.count() == 0:
        return
    utils.write_to_elasticsearch(df, utils.ELASTIC_USERS_INTERACTION_INDEX_NAME, "append")
    print(f"🖨️ Wrote batch_id {batch_id} to Elasticsearch {utils.ELASTIC_USERS_INTERACTION_INDEX_NAME} index.")


def write_stream_to_elasticsearch(df: DataFrame) -> StreamingQuery:
    query = df.withColumn("release_date", date_format(col("release_date"), "yyyy-MM-dd")) \
        .writeStream \
        .outputMode("append") \
        .foreachBatch(write_interactions_to_elasticsearch) \
        .start()
    print("✅ Streaming started. Awaiting termination...")
    query.awaitTermination()
    return query


def main() -> None:
    spark = create_spark_session()
    utils.ensure_es_index(utils.ELASTIC_USERS_INTERACTION_INDEX_NAME, utils.USERS_INTERACTION_INDEX_MAPPING)
    df_books_metadata = utils.read_from_postgres(spark, utils.BOOKS_METADATA_TABLE_NAME)
    df_kafka_raw = utils.read_kafka_stream(spark, utils.KAFKA_USERS_INTERACTION_TOPIC_NAME)
    df_kafka_json = utils.parse_kafka_json(df_kafka_raw, utils.KAFKA_USERS_INTERACTION_SCHEMA)
    df_kafka_postgres = utils.join_interaction_to_book_metadata(df_kafka_json, df_books_metadata)
    if ENABLE_CONSOLE_MODE:
        write_stream_to_console(df_kafka_postgres)
    else:
        write_stream_to_elasticsearch(df_kafka_postgres)

if __name__ == "__main__":
    main()