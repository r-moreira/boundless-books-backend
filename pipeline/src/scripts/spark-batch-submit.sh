#!/bin/bash

AWS_JAVA_SDK="com.amazonaws:aws-java-sdk-bundle:1.12.783"
HADOOP_AWS="org.apache.hadoop:hadoop-aws:3.3.4"
ELASTICSEARCH_SPARK="org.elasticsearch:elasticsearch-spark-30_2.12:8.13.4"
POSTGRESQL_JDBC="org.postgresql:postgresql:42.6.0"

PACKAGES="$POSTGRESQL_JDBC,$AWS_JAVA_SDK,$HADOOP_AWS,$ELASTICSEARCH_SPARK"

docker exec -it spark spark-submit \
  --packages $PACKAGES \
  --conf spark.jars.ivy=/opt/bitnami/spark/jars/ \
  --py-files /work/utils.py \
  /work/spark_batch.py
