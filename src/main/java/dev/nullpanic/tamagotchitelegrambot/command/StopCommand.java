package dev.nullpanic.tamagotchitelegrambot.command;

import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String STOP_MESSAGE = "Бот остановлен. Возвращайся скорее без тебя совсем скучно! (:";

    public StopCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        telegramUserService.findByChatId(chatId).ifPresent(
                user -> {
                    user.setActive(false);
                    telegramUserService.save(user);
                });

        sendBotMessageService.sendMessage(chatId, STOP_MESSAGE);
    }
}
