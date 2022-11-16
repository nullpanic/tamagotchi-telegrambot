package dev.nullpanic.tamagotchitelegrambot.command;

import com.google.common.collect.ImmutableMap;
import dev.nullpanic.tamagotchitelegrambot.command.petcommand.*;
import dev.nullpanic.tamagotchitelegrambot.service.PetService;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUpdateService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUserService;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(
            SendBotMessageService sendBotMessageService,
            TelegramUserService telegramUserService,
            PetService petService,
            TelegramUpdateService telegramUpdateService
    ) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(CommandName.START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(CommandName.STOP_ALL.getCommandName(), new StopAllPetsCommand(sendBotMessageService, telegramUpdateService, petService))
                .put(CommandName.HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(CommandName.STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(CommandName.CREATE.getCommandName(), new CreatePetCommand(sendBotMessageService, petService, telegramUpdateService))
                .put(CommandName.FEED.getCommandName(), new FeedPetCommand(sendBotMessageService, petService, telegramUpdateService))
                .put(CommandName.GET_ALL_PETS.getCommandName(), new GetAllPetsCommand(sendBotMessageService, petService, telegramUpdateService))
                .put(CommandName.STOP.getCommandName(), new StopPetCommand(sendBotMessageService, telegramUpdateService, petService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
