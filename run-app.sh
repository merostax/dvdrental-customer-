#!/bin/bash

# Set PostgreSQL database connection details
export QUARKUS_DATASOURCE_DB_KIND=dvdrentalcustomer
export QUARKUS_DATASOURCE_USERNAME=postgres
export QUARKUS_DATASOURCE_PASSWORD=postgres
export QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://localhost:54323/dvdrentalcustomer
#export QUARKUS_HTTP_PORT=8083

# Start your Quarkus application
./dvdrental-customer-1.0-runner

