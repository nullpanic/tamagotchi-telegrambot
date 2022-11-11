package dev.nullpanic.tamagotchitelegrambot.command;

import static dev.nullpanic.tamagotchitelegrambot.command.CommandName.STAT;
import static dev.nullpanic.tamagotchitelegrambot.command.StatCommand.STAT_MESSAGE;

class StatCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return String.format(STAT_MESSAGE, 0);
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }
}