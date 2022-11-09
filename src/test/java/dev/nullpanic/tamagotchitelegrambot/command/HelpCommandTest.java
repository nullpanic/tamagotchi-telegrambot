package dev.nullpanic.tamagotchitelegrambot.command;

import org.junit.jupiter.api.DisplayName;

import static dev.nullpanic.tamagotchitelegrambot.command.CommandName.HELP;
import static dev.nullpanic.tamagotchitelegrambot.command.HelpCommand.HELP_MESSAGE;

@DisplayName("Unit-level testing for HelpCommand")
class HelpCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new HelpCommand(sendBotMessageService);
    }
}