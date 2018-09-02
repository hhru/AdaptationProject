FROM anapsix/alpine-java

ENV APP_DIR="/usr/share/adapt"
ENV JAR="$APP_DIR/adaptation-project-2.0-jar-with-dependencies.jar"
ENV SETTINGS_DIR="$APP_DIR/classes"
ENV TEMPLATES_DIR="/usr/share/templates"

ADD target /usr/share/adapt

CMD ["sh", "-c", "exec java -DsettingsDir=$SETTINGS_DIR -DtemplatesDir=$TEMPLATES_DIR -jar $JAR"]
