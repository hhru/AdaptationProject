FROM anapsix/alpine-java

ENV CONFIG_DIR="/etc/adapt"
ENV LOG_DIR="/var/log/adapt"
ENV JAR="/usr/share/adapt/adapt.jar"

ADD target/adaptation-project-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/share/adapt/adapt.jar

RUN mkdir /var/log/adapt

#EXPOSE 9999

#CMD ["sh", "-c", "exec java -DsettingsDir=$CONFIG_DIR -jar $JAR >> \"$LOG_DIR/adapt.log\" 2>&1"]
CMD ["sh", "-c", "exec java -DsettingsDir=$CONFIG_DIR -jar $JAR"]
