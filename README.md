# tamagotchi-telegrambot 0.10.0-SNAPSHOT 

## Available commands:
>### Global:
> * /get_all_pets - get a list of all pets
> * /stop_all - freeze all pets 
> * /start - unfreeze all pets
>### Pet commands:
> * /create name - create pet  
> * /feed name - feed pet

## Deployment:
>Deployment process as easy as possible:
>
>#### Required software:
>- terminal for running shell scripts
>- docker
>- docker-compose
>#### to deploy application:
>#### 1) In application.properties change:  
>spring.datasource.url=jdbc:postgresql://localhost:5432/tamagotchi_db  
>to spring.datasource.url=jdbc:postgresql://tamagotchi-db:5432/tamagotchi_db
>#### 2) Switch into needed branch and run zsh/bash script: 
>zsh example: `$ bash start.sh ${bot_username} ${bot_token} ${db_user} ${db_password}`
## Technology stack:
>- Maven 
>- Java 18
>- Spring boot \ Spring data
>- PostgreSQL \ Liquibase \ Hibernate
>- Docker
>- JUnit 5 \ Mockito \ Jacoco \ lombok
>- Telegrambot spring boot starter
>- Github Actions CI/CD

!
