package dev.nullpanic.tamagotchitelegrambot.command;

import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.nullpanic.tamagotchitelegrambot.command.CommandName.START;
import static dev.nullpanic.tamagotchitelegrambot.command.CommandName.STOP;

public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public final static String HELP_MESSAGE = String.format("""
                    <b>Доступные команды</b>
                                
                    <b>Начать\\закончить работу с ботом</b>
                    %s - начать работу бота
                    %s - закончить работу бота (Состояние всех питомцев будет сохранено)""",
            START.getCommandName(),
            STOP.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), HELP_MESSAGE);
    }
}
