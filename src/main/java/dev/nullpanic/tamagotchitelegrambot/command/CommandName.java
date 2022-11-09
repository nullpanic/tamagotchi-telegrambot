package dev.nullpanic.tamagotchitelegrambot.command;

import lombok.Getter;

@Getter
public enum CommandName {

    START("/start"),
    STOP("/stop"),
    HELP("/help");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }
}
