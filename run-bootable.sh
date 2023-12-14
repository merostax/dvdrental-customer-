#!/bin/bash

mvn clean package
sleep 5
java -jar -Ddvdrental.jdbc.url=jdbc:postgresql://localhost:5432/dvdrentalcustomer -Dcustomer.service.uri=http://localhost:8083/ -Dstore.service.uri=http://localhost:8082/ -Dfilm.service.uri=http://localhost:8081/ -Djboss.http.port=8083 -Djboss.management.http.port=9993 target/starter-bootable.jar

