package dev.nullpanic.tamagotchitelegrambot.command;

import org.junit.jupiter.api.DisplayName;

import static dev.nullpanic.tamagotchitelegrambot.command.CommandName.STOP;
import static dev.nullpanic.tamagotchitelegrambot.command.StopCommand.STOP_MESSAGE;

@DisplayName("Unit-level testing for StopCommand")
class StopCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return STOP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return STOP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StopCommand(sendBotMessageService);
    }
}