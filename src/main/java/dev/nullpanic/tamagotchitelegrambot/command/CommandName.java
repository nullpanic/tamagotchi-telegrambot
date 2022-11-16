package dev.nullpanic.tamagotchitelegrambot.command;

import lombok.Getter;

@Getter
public enum CommandName {

    START("/start"),
    STOP_ALL("/stop_all"),
    HELP("/help"),
    STAT("/stat"),
    CREATE("/create"),
    FEED("/feed"),
    GET_ALL_PETS("/get_all_pets"),
    STOP("/stop");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }
}
