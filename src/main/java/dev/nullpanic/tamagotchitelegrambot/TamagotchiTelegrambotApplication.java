package dev.nullpanic.tamagotchitelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TamagotchiTelegrambotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TamagotchiTelegrambotApplication.class, args);
    }

}
