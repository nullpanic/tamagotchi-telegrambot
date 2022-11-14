package dev.nullpanic.tamagotchitelegrambot.command;

import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.nullpanic.tamagotchitelegrambot.command.CommandName.*;

public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public final static String HELP_MESSAGE = String.format("""
                    <b>Доступные команды</b>
                                
                    <b>Начать\\закончить работу с ботом</b>
                    %s - начать работу бота
                    %s - закончить работу бота (Состояние всех питомцев будет сохранено)
                    <b>Питомцы</b>
                    %s name - создать питомца
                    %s name - покормить питомца""",
            START.getCommandName(),
            STOP.getCommandName(),
            CREATE.getCommandName(),
            FEED.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), HELP_MESSAGE);
    }
}
