package dev.nullpanic.tamagotchitelegrambot.service.impl;

import dev.nullpanic.tamagotchitelegrambot.service.TelegramUpdateService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Service
public class TelegramUpdateServiceImpl implements TelegramUpdateService {

    @Override
    public Long getChatId(Update update) {
        return update.getMessage().getChatId();
    }

    @Override
    public String[] getSplitCommand(Update update, String regexp) {
        return Arrays.stream(update.getMessage().getText().split(regexp))
                .map(String::trim)
                .toArray(String[]::new);
    }

    @Override
    public String removeExtraSpaces(String command) {
        return command.replaceAll("[\\s]{2,}", " ");
    }
}
