# tamagotchi-telegrambot
Tamagotchi telegrambot

## Deployment
Deployment process as easy as possible:

Required software:
- terminal for running bash scripts
- docker
- docker-compose

to deploy application:
1) In application.properties change:  
`spring.datasource.url=jdbc:postgresql://localhost:5432/tamagotchi_db` to `spring.datasource.url=jdbc:postgresql://tamagotchi-db:5432/tamagotchi_db`

2) Switch into needed branch and run bash script: 
`$ bash start.sh ${bot_username} ${bot_token} ${db_user} ${db_password}`

That's all.
