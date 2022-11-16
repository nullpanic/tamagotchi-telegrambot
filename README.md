## tamagotchi-telegrambot

## Current version: 

### Available commands:

#### Start\stop commands 

>/start - start bot  
>/stop - stop bot (The state of all pets will ve saved) 

#### Pet commands  

>/create name - create pet  
>/feed name - feed pet  
>/get_all_pets - get a list of all pets

## Deployment
Deployment process as easy as possible:

Required software:
- terminal for running shell scripts
- docker
- docker-compose

### to deploy application:
#### 1) In application.properties change:  
>spring.datasource.url=jdbc:postgresql://localhost:5432/tamagotchi_db  
>to  
>spring.datasource.url=jdbc:postgresql://tamagotchi-db:5432/tamagotchi_db
#### 2) Switch into needed branch and run Zsh/bash script: 
>Zsh example: `$ bash start.sh ${bot_username} ${bot_token} ${db_user} ${db_password}`

### That's all.
