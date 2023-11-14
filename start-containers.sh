#!/bin/bash

# Define container names
FILM_DB_CONTAINER="film-db"
STORE_DB_CONTAINER="store-db"
CUSTOMER_DB_CONTAINER="customer-db"

# Define port mappings
FILM_DB_PORT=54321
STORE_DB_PORT=54322
CUSTOMER_DB_PORT=54323

# Pull the PostgreSQL 15 image
podman pull docker.io/library/postgres:15
podman tag docker.io/library/postgres:15 ftse/postgres:15

# Start PostgreSQL containers without a pod, each with specific ports
podman run -d --name $FILM_DB_CONTAINER -p $FILM_DB_PORT:5432 -e POSTGRES_DB=film-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres ftse/postgres:15

podman run -d --name $STORE_DB_CONTAINER -p $STORE_DB_PORT:5432 -e POSTGRES_DB=store-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres ftse/postgres:15

podman run -d --name $CUSTOMER_DB_CONTAINER -p $CUSTOMER_DB_PORT:5432 -e POSTGRES_DB=customer-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres ftse/postgres:15
