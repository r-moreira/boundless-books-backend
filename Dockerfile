FROM ubuntu:22.04

RUN apt-get update
RUN apt-get install -y openjdk-21-jdk openjdk-21-jre maven

COPY . /spring-boot-app

WORKDIR /spring-boot-app

RUN mvn clean install

WORKDIR /spring-boot-app/target

CMD ["java","-jar","boundless-books-0.0.1-SNAPSHOT.jar"]