FROM zalando/openjdk:8u45-b14-5
MAINTAINER Team Spearheads <team-spearheads@zalando.de>

EXPOSE 8080 8080

RUN mkdir -p /opt/trout

ADD target/scala-2.11/trout-assembly-0.0.1.jar /opt/trout/

WORKDIR /opt/trout

ENTRYPOINT java $(java-dynamic-memory-opts) -Dtrout.etcd.uri=$ETCD_URL -server -jar trout-assembly-0.0.1.jar
