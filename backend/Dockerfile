FROM maven:3-jdk-8-alpine AS builder

ARG ADAPT_BUILD_MODE

ARG ADAPT_JDBC_URL
ARG ADAPT_JDBC_USER
ARG ADAPT_JDBC_PASSWORD

ARG ADAPT_OAUTH_URL
ARG ADAPT_OAUTH_CLIENT_ID
ARG ADAPT_OAUTH_CLIENT_SECRET

ARG ADAPT_SMTP_HOST
ARG ADAPT_SMTP_USER
ARG ADAPT_SMTP_PASSWORD

ARG ADAPT_EMAIL1
ARG ADAPT_EMAIL2


COPY src /usr/src/adapt/src
COPY pom.xml /usr/src/adapt
COPY checkstyle.xml /usr/src/adapt

WORKDIR /usr/src/adapt

RUN mvn clean install



FROM boxfuse/flyway:5-alpine

COPY --from=builder /usr/src/adapt/target /usr/share/adapt
COPY src/main/resources/db/migration /flyway/sql
COPY run.sh /usr/share/adapt

RUN chmod +x /usr/share/adapt/run.sh

WORKDIR /usr/share/adapt

ENTRYPOINT /usr/share/adapt/run.sh
