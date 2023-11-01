#!/bin/bash

# Stop and remove all running containers
podman stop --all
podman rm --all

# Remove all pods
podman pod stop --all
podman pod rm --all

# Remove all images (including dangling ones)
podman rmi --all
