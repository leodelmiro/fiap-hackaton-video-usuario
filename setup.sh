#!/bin/bash

# Access local folder
cd local-usuario || exit

# Stop and remove containers, networks, images, and volumes
docker-compose down -v

# Return to project folder
cd .. || exit

# Clean and build the Maven project
./mvnw clean install

# Access local folder
cd local-usuario || exit

# Start the containers in detached mode and build the images
docker-compose up -d --build