#!/bin/bash

export POSTGRESQL_SERVICE_HOST=localhost
export POSTGRESQL_SERVICE_PORT=54323
export POSTGRESQL_USER=postgres
export POSTGRESQL_PASSWORD=trust


mvn clean package
sleep 5
java -jar -Djboss.http.port=8083 -Djboss.management.http.port=9993 target/starter-bootable.jar

