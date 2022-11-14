package dev.nullpanic.tamagotchitelegrambot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramUpdateService {

    Long getChatId(Update update);

    String[] getSplitCommand(Update update, String regexp);

    String removeExtraSpaces(String command);
}
