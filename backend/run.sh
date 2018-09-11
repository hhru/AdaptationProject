#!/bin/sh

flyway migrate -configFiles=classes/flyway.conf
exec java -DsettingsDir=classes -DtemplatesDir=templates -jar *.jar
