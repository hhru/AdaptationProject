#!/bin/sh

flyway migrate -configFiles=classes/flyway.conf
exec java -DsettingsDir=classes -DtemplatesDir=templates -DuploadDir=upload -jar *.jar
