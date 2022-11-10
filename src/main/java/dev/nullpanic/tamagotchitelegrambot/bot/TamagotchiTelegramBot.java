package dev.nullpanic.tamagotchitelegrambot.bot;

import dev.nullpanic.tamagotchitelegrambot.command.CommandContainer;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageServiceImpl;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class TamagotchiTelegramBot extends TelegramLongPollingBot {
    private static final String COMMAND_PREFIX = "/";
    @Value("${telegramBot.username}")
    private String username;

    @Value("${telegramBot.token}")
    private String token;

    private final CommandContainer commandContainer;

    @Autowired
    public TamagotchiTelegramBot(TelegramUserService telegramUserService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), telegramUserService);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String commandIdentifier = message.split(" ")[0].toLowerCase();

            commandContainer.retrieveCommand(commandIdentifier).execute(update);
        }
    }
}
