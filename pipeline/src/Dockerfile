FROM docker.io/bitnami/spark:3.5

USER root

RUN apt-get update && apt-get install -y python3-pip wget unzip

RUN wget https://downloads.apache.org/ant/ivy/2.5.2/apache-ivy-2.5.2-bin.zip -O /tmp/ivy.zip && \
    unzip /tmp/ivy.zip -d /opt && \
    mv /opt/apache-ivy-2.5.2/ivy-2.5.2.jar /opt/bitnami/spark/jars/

COPY requirements.txt /tmp/requirements.txt

COPY pyspark_drivers/  /opt/bitnami/spark/jars

RUN pip3 install --no-cache-dir -r /tmp/requirements.txt

EXPOSE 8080 8081 7077 4040