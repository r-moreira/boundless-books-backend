#!/bin/bash

SPARK_KAFKA="org.apache.spark:spark-sql-kafka-0-10_2.12:3.5.0"
ELASTICSEARCH_SPARK="org.elasticsearch:elasticsearch-spark-30_2.12:8.13.4"
POSTGRESQL_JDBC="org.postgresql:postgresql:42.6.0"

PACKAGES="$SPARK_KAFKA,$ELASTICSEARCH_SPARK,$POSTGRESQL_JDBC"

docker exec -it spark spark-submit \
  --packages $PACKAGES \
  --conf spark.jars.ivy=/opt/bitnami/spark/jars \
  --py-files /work/utils.py \
  /work/spark_ml.py

