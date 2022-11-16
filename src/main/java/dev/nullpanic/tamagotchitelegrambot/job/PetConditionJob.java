package dev.nullpanic.tamagotchitelegrambot.job;

import dev.nullpanic.tamagotchitelegrambot.service.PetConditionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
public class PetConditionJob {

    private final PetConditionService petConditionService;

    @Autowired
    public PetConditionJob(PetConditionService petConditionService) {
        this.petConditionService = petConditionService;
    }

    @Scheduled(fixedRateString = "${telegramBot.recountDecreasePetsStatus}")
    public void decreasePetsStatus() {
        LocalDateTime start = LocalDateTime.now();

        log.info("Decrease pets status job started.");

        petConditionService.decreasePetsStatus();
        petConditionService.sendLowHungrinessPetNotifications();

        LocalDateTime end = LocalDateTime.now();

        log.info("Decrease pets status job finished. Took seconds: {}",
                end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC));
    }
}
