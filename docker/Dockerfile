FROM openjdk:17-jdk-slim

ENV PORT ${PORT:-8080}
ARG container_user=fayda
ARG container_user_group=fayda
ARG container_user_uid=1001
ARG container_user_gid=1001
RUN apt-get -y update \
&& groupadd -g ${container_user_gid} ${container_user_group} \
&& useradd -u ${container_user_uid} -g ${container_user_group} -s /bin/sh -m ${container_user} \
&& adduser ${container_user} \

EXPOSE ${PORT}

VOLUME /tmp

ADD /maven/*.jar /opt/app.jar

CMD java -jar /opt/app.jar