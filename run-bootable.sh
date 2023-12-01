#!/usr/bin/bash
source .env
mvn clean  package
wait $!

DOCKERFILE_DB_PATH="./docker/db/Dockerfile"
DOCKERFILE_PATH="./docker/app/Dockerfile"

if podman pod exists $PODNAME; then
    echo "Pod $PODNAME already exists. Skipping pod creation."
else
    podman pod create --name $PODNAME -p 54323:54323 -p 54321:54321 -p 54322:54322 -p 8083:8083 -p 8082:8082 -p 8081:8081
fi
wait_for_postgres() {
    until podman exec -it $DB_CONTAINER_ID psql -p 54323 -U postgres -d dvdrentalcustomer -c '\q' &> /dev/null; do
        echo "Waiting for PostgreSQL to be ready..."
        sleep 5
    done
}

 podman build -t $CUSTOMER_CONTAINER_NAME_POSTGRES --build-arg SQL_FILE=$CUSTOMER_DB_SQL -f $DOCKERFILE_DB_PATH .
DB_CONTAINER_ID=$(podman run -d --pod  $PODNAME  $CUSTOMER_CONTAINER_NAME_POSTGRES)

wait_for_postgres

 podman build -t $CUSTOMER_CONTAINER_NAME_WILDFLY -f $DOCKERFILE_PATH .
 podman run  -d --pod $PODNAME  -e POSTGRESQL_DB=dvdrentalcustomer -e POSTGRESQL_USER=postgres  -e POSTGRESQL_PASSWORD=postgres $CUSTOMER_CONTAINER_NAME_WILDFLY
