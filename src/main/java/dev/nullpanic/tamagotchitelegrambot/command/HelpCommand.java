package dev.nullpanic.tamagotchitelegrambot.command;

import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.nullpanic.tamagotchitelegrambot.command.CommandName.*;

public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public final static String HELP_MESSAGE = String.format("""
                    <b>Доступные команды</b>
                                
                    <b>Общие</b>
                    %s - запустить бота
                    %s - остановить бота
                    %s - список всех питомцев
                                        
                    <b>Действия с питомцами</b>
                    %s name - создать питомца
                    %s name - покормить питомца""",
            START.getCommandName(),
            STOP.getCommandName(),
            GET_ALL_PETS.getCommandName(),
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
