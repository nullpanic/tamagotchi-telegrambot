#!/bin/bash

# Pull new changes
git pull

# Add environment variables
export TEST_TAMAGOTCHI_BOT_USERNAME=$1
export TEST_TAMAGOTCHI_BOT_TOKEN=$2

# Prepare Jar
mvn clean
mvn package

# Ensure, that docker-compose stopped
docker-compose stop

# Start new deployment
docker-compose up --build -d