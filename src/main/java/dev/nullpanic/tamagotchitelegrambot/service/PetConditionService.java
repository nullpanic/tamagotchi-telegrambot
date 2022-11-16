package dev.nullpanic.tamagotchitelegrambot.service;

public interface PetConditionService {

    void decreasePetsStatus();

    void sendLowHungrinessPetNotifications();
}
