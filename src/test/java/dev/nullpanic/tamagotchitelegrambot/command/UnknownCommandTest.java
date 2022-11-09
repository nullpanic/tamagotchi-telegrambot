package dev.nullpanic.tamagotchitelegrambot.command;

import org.junit.jupiter.api.DisplayName;

import static dev.nullpanic.tamagotchitelegrambot.command.UnknownCommand.UNKNOWN_MESSAGE;

@DisplayName("Unit-level testing for UnknownCommand")
class UnknownCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return "dasdasd/asd";
    }

    @Override
    String getCommandMessage() {
        return UNKNOWN_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new UnknownCommand(sendBotMessageService);
    }
}