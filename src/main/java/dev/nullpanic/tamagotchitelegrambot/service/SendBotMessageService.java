package dev.nullpanic.tamagotchitelegrambot.service;

public interface SendBotMessageService {

    void sendMessage(Long chatId, String message);
}
