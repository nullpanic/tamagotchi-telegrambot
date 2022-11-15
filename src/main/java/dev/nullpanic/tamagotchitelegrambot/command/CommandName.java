package dev.nullpanic.tamagotchitelegrambot.command;

import lombok.Getter;

@Getter
public enum CommandName {

    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    STAT("/stat"),
    CREATE("/create"),
    FEED("/feed"),
    GET_ALL_PETS("/get_all_pets");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }
}
