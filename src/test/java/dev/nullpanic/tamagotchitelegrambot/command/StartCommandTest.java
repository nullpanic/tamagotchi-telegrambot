package dev.nullpanic.tamagotchitelegrambot.command;

import static dev.nullpanic.tamagotchitelegrambot.command.CommandName.START;
import static dev.nullpanic.tamagotchitelegrambot.command.StartCommand.START_MESSAGE;

class StartCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return START.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return START_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StartCommand(sendBotMessageService, telegramUserService);
    }
}