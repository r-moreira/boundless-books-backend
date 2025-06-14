import tempfile
from io import BytesIO
from typing import List, Dict
import base64
from PIL import Image
import boto3
import ebooklib
import epub_metadata
from botocore.client import Config
from bs4 import BeautifulSoup
from ebooklib import epub
from pyspark.sql import SparkSession
from pyspark.sql.functions import col, to_date, current_timestamp

import utils

def create_spark_session():
    print("🚀 Starting Spark Session setup...")
    spark = SparkSession.builder \
        .appName("Batch Spark") \
        .config("spark.jars.packages", ",".join( [
            "org.postgresql:postgresql:42.6.0",
            "com.amazonaws:aws-java-sdk-bundle:1.12.783",
            "org.apache.hadoop:hadoop-aws:3.3.4",
            "org.elasticsearch:elasticsearch-spark-30_2.12:8.13.4"
        ])) \
        .config("spark.hadoop.fs.s3a.endpoint", utils.S3_CONFIG["endpoint_url"]) \
        .config("spark.hadoop.fs.s3a.access.key", utils.S3_CONFIG["aws_access_key_id"]) \
        .config("spark.hadoop.fs.s3a.secret.key", utils.S3_CONFIG["aws_secret_access_key"]) \
        .config("spark.hadoop.fs.s3a.path.style.access", "true") \
        .config("spark.hadoop.fs.s3a.connection.ssl.enabled", "false") \
        .config("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem") \
        .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    spark.sparkContext.addPyFile("/work/utils.py")
    print("✅ Spark Session started.")
    return spark


def upload_cover_image_to_s3(_epub, s3_client, bucket_name: str, key_prefix: str = "") -> str:
    print(f"📤 Uploading cover image to S3 bucket '{bucket_name}'...")
    try:
        cover_base64 = _epub.metadata.cover
        if not cover_base64:
            print("⚠️ No cover found in EPUB metadata.")
            return ""
        cover_data = base64.b64decode(cover_base64)
        image = Image.open(BytesIO(cover_data)).convert("RGB")
        safe_title = _epub.metadata.title.replace("/", "-").strip() if _epub.metadata.title else "unknown_title"
        safe_author = _epub.metadata.creator.replace("/", "-").strip() if _epub.metadata.creator else "unknown_author"
        filename = f"{safe_title} - {safe_author}_cover.jpeg"
        s3_key = f"{key_prefix}/{filename}" if key_prefix else filename
        image_bytes = BytesIO()
        image.save(image_bytes, format='JPEG')
        image_bytes.seek(0)
        s3_client.upload_fileobj(
            Fileobj=image_bytes,
            Bucket=bucket_name,
            Key=s3_key,
            ExtraArgs={
                "ContentType": "image/jpeg",
                "ACL": "public-read"
            }
        )
        public_url = f"s3://{bucket_name}/{s3_key}"
        print(f"✅ Cover image uploaded: {public_url}")
        return public_url
    except Exception as e:
        print(f"❌ Failed to upload cover: {e}")
        return ""


def get_metadata_from_epub(_epub, cover_url: str, epub_url: str) -> Dict:
    print(f"🔍 Extracting metadata from EPUB: {_epub.metadata.title}")
    soup = BeautifulSoup(_epub.metadata.description or "", "html.parser")
    metadata = {
        "title": _epub.metadata.title or "",
        "author": _epub.metadata.creator or "",
        "publisher": _epub.metadata.publisher or "",
        "category": "",
        "synopsis": soup.get_text(),
        "release_date": "1970-01-01",
        "pages": -1,
        "cover_image_url": cover_url,
        "epub_url": epub_url
    }
    print(f"✅ Metadata extracted: {metadata['title']} by {metadata['author']}")
    return metadata


def process_epub_from_s3(key: str, s3_config: Dict, raw_bucket: str, processed_bucket: str) -> Dict:
    print(f"📖 Processing EPUB from S3 key: {key}")
    try:
        s3_client = boto3.client("s3", config=Config(signature_version="s3v4"), **s3_config)
        print(f"⬇️ Downloading EPUB file from bucket '{raw_bucket}', key '{key}'...")
        obj = s3_client.get_object(Bucket=raw_bucket, Key=key)
        epub_bytes = obj["Body"].read()
        print(f"✅ Downloaded {len(epub_bytes)} bytes.")
        with tempfile.NamedTemporaryFile(delete=True, suffix=".epub") as tmp:
            tmp.write(epub_bytes)
            tmp.flush()
            print(f"📂 Saved EPUB to temporary file {tmp.name}")
            epub_meta = epub_metadata.epub(tmp.name)
            book = epub.read_epub(tmp.name)
            print("📝 Extracting text content from EPUB...")
            content = []
            for item in book.get_items_of_type(ebooklib.ITEM_DOCUMENT):
                soup = BeautifulSoup(item.get_content(), 'html.parser')
                text = soup.get_text(separator='\n', strip=True)
                if text:
                    content.append(text)
            joined_content = "\n".join(content)
            print(f"Extracted text content length: {len(joined_content)} characters")
            cover_url = upload_cover_image_to_s3(epub_meta, s3_client, processed_bucket, "cover")
            processed_epub_key = f"epub/{key}"
            utils.move_object_between_buckets(s3_client, raw_bucket, key, processed_bucket, processed_epub_key)
            epub_url = f"s3://{processed_bucket}/{processed_epub_key}"
            metadata = get_metadata_from_epub(epub_meta, cover_url, epub_url)
            content_record = {
                "id": epub_url,
                "title": metadata["title"],
                "author": metadata["author"],
                "content": joined_content
            }
            print(f"✅ Finished processing EPUB '{metadata['title']}'")
            return {
                "metadata": metadata,
                "content": content_record
            }
    except Exception as e:
        error_msg = f"❌ Error processing EPUB '{key}': {e}"
        print(error_msg)
        return {"error": str(e), "epubUrl": f"s3://{processed_bucket}/{key}"}


def write_metadata_to_postgres(spark: SparkSession, records: List[Dict], table_name: str) -> None:
    print("💾 Writing metadata to PostgreSQL...")
    metadata_only = [r["metadata"] for r in records if "metadata" in r]
    if not metadata_only:
        print("⚠️ No metadata records to write.")
        return
    df = spark.createDataFrame(metadata_only) \
        .withColumn("release_date", to_date(col("release_date"), "yyyy-MM-dd")) \
        .withColumn("created_at", current_timestamp()) \
        .withColumn("updated_at", current_timestamp())
    utils.write_to_postgres(df, table_name, "append")
    print(f"✅ Wrote {len(metadata_only)} metadata records to PostgreSQL.")


def write_content_to_elasticsearch(spark: SparkSession, records: List[Dict], index_name: str) -> None:
    print(f"💾 Writing content to Elasticsearch index '{index_name}'...")
    content_only = [r["content"] for r in records if "content" in r]
    if not content_only:
        print("⚠️ No content records to write.")
        return
    df = spark.createDataFrame(content_only)
    utils.write_to_elasticsearch(df, utils.ELASTIC_BOOKS_CONTENT_INDEX_NAME, "overwrite")
    print(f"✅ Wrote {len(content_only)} content records to Elasticsearch.")


def main():
    print("🚀 Starting main process...")
    spark = create_spark_session()
    s3_client = utils.create_s3_client()
    utils.ensure_postgres_table()
    utils.ensure_es_index(utils.ELASTIC_BOOKS_CONTENT_INDEX_NAME, utils.ELASTICSEARCH_BOOKS_CONTENT_MAPPING)
    utils.ensure_s3_buckets(s3_client, [utils.RAW_BUCKET_NAME, utils.PROCESSED_BUCKET_NAME])
    epub_keys = utils.list_s3_keys(s3_client, bucket_name=utils.RAW_BUCKET_NAME)
    if not epub_keys:
        print("⚠️ No EPUB files found in MinIO.")
        return
    print(f"📦 Found {len(epub_keys)} EPUB files to process.")
    s3_config_bc = spark.sparkContext.broadcast(utils.S3_CONFIG)
    rdd = spark.sparkContext.parallelize(epub_keys)
    results = rdd.map(
        lambda key: process_epub_from_s3(
            key,
            s3_config_bc.value,
            utils.RAW_BUCKET_NAME,
            utils.PROCESSED_BUCKET_NAME
        )
    ).collect()
    valid_results = [r for r in results if "error" not in r]
    if valid_results:
        write_metadata_to_postgres(spark, valid_results, utils.BOOKS_METADATA_TABLE_NAME)
        write_content_to_elasticsearch(spark, valid_results, utils.ELASTIC_BOOKS_CONTENT_INDEX_NAME)
        print(f"✅ {len(valid_results)} books processed: metadata to PostgreSQL, content to Elasticsearch.")
    else:
        print("⚠️ No valid EPUB files processed.")
    spark.stop()

if __name__ == "__main__":
    main()