FROM anapsix/alpine-java

ENV CONFIGS_DIR="/etc/adapt/configs"
ENV TEMPLATES_DIR="/etc/adapt/templates"
ENV LOG_DIR="/var/log/adapt"
ENV JAR="/usr/share/adapt/adapt.jar"

ADD target/adaptation-project-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/share/adapt/adapt.jar

RUN mkdir /var/log/adapt /etc/adapt

#EXPOSE 9999

CMD ["sh", "-c", "exec java -DsettingsDir=$CONFIGS_DIR -DtemplatesDir=$TEMPLATES_DIR -jar $JAR"]

