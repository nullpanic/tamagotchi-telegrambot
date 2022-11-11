package dev.nullpanic.tamagotchitelegrambot.command;

import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String STOP_MESSAGE = "Увидимся! Не забывай навещать меня и своих питомцев (:";

    public StopCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), STOP_MESSAGE);
        telegramUserService.findByChatId(update.getMessage().getChatId())
                .ifPresent(user -> {
                    user.setActive(false);
                    telegramUserService.save(user);
                });
    }
}
