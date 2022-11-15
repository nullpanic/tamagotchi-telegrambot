package dev.nullpanic.tamagotchitelegrambot.service.impl;

import dev.nullpanic.tamagotchitelegrambot.bot.TamagotchiTelegramBot;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final TamagotchiTelegramBot tamagotchiTelegramBot;

    @Autowired
    public SendBotMessageServiceImpl(TamagotchiTelegramBot tamagotchiTelegramBot) {
        this.tamagotchiTelegramBot = tamagotchiTelegramBot;
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            tamagotchiTelegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            //todo add logging to the project
            e.printStackTrace();
        }
    }
}
