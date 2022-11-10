package dev.nullpanic.tamagotchitelegrambot.command;

import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService);
    }

    @Test
    public void testCommandContainer_WhenInit_ShouldContainAllExistingCommands() {
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    public void testRetrieveCommand_WhenArgCommandIdentifierUnknown_ShouldReturnUnknownCommand() {
        String commandIdentifier = "falkjfhaljkhr1edafls;d";

        Command command = commandContainer.retrieveCommand(commandIdentifier);

        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}