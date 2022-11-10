#!/bin/bash

# Pull new changes
git pull

# Add environment variables
export TEST_TAMAGOTCHI_BOT_USERNAME=$1
export TEST_TAMAGOTCHI_BOT_TOKEN=$2
export DB_USER_LOCAL=$3
export DB_PASSWORD_LOCAL=$4

# Prepare Jar
mvn clean package -DskipTests

# Ensure, that docker-compose stopped
docker-compose stop

# Start new deployment
docker-compose up --build -d
