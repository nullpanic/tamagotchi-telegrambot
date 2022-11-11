package dev.nullpanic.tamagotchitelegrambot.command;

import static dev.nullpanic.tamagotchitelegrambot.command.CommandName.CREATE;
import static dev.nullpanic.tamagotchitelegrambot.command.CreatePetCommand.CREATE_PET_MESSAGE;

class CreatePetCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return CREATE.getCommandName() + " Charlie";
    }

    @Override
    String getCommandMessage() {
        return String.format(CREATE_PET_MESSAGE, "Charlie", 100, 100, 100, 100, 100);
    }

    @Override
    Command getCommand() {
        return new CreatePetCommand(sendBotMessageService, petService);
    }
}