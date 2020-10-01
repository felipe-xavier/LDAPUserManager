FROM maven:3.6.3-jdk-14 AS build
LABEL MAINTAINER="Felipe Almeida"

ADD . /opt/app

RUN set -ex \
  && cd /opt/app \
  && mvn clean install

FROM openjdk:11
LABEL MAINTAINER="Felipe Almeida"

ENV JAR_FILE="interview-0.1.0.jar"
COPY --from=build /opt/app/target/${JAR_FILE} /${JAR_FILE}

CMD ["java", "-jar", "/interview-0.1.0.jar"]