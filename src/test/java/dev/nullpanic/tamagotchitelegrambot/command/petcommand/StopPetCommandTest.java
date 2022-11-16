package dev.nullpanic.tamagotchitelegrambot.command.petcommand;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;
import dev.nullpanic.tamagotchitelegrambot.service.PetService;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUpdateService;
import dev.nullpanic.tamagotchitelegrambot.service.impl.TelegramUpdateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static dev.nullpanic.tamagotchitelegrambot.command.CommandName.STOP;
import static dev.nullpanic.tamagotchitelegrambot.command.petcommand.StopPetCommand.*;
import static org.mockito.ArgumentMatchers.any;

class StopPetCommandTest {
    private final SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
    private final TelegramUpdateService telegramUpdateService = new TelegramUpdateServiceImpl();
    private final PetService petService = Mockito.mock(PetService.class);
    private Message message;
    private final Update update = new Update();
    private final Long CHAT_ID = 1L;

    @BeforeEach
    public void init() {
        message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(CHAT_ID);
    }


    @ParameterizedTest
    @ValueSource(strings = {"/stop     ", "/stop"})
    public void testExecute_WhenPetNameIsBlank_ShouldSendPetNameIsBlankMessage(String inputCommand) {
        Mockito.when(message.getText()).thenReturn(inputCommand);

        update.setMessage(message);

        StopPetCommand stopPetCommand = new StopPetCommand(sendBotMessageService, telegramUpdateService, petService);
        stopPetCommand.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(CHAT_ID, PET_NAME_IS_BLANK_MESSAGE);
    }

    @Test
    public void testExecute_WhenPetNotFound_ShouldSendPetNotFoundMessage() {
        String petName = "charlie";
        String inputCommand = STOP.getCommandName() + " " + petName;

        Mockito.when(message.getText()).thenReturn(inputCommand);

        update.setMessage(message);

        StopPetCommand stopPetCommand = new StopPetCommand(sendBotMessageService, telegramUpdateService, petService);
        stopPetCommand.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(CHAT_ID, String.format(PET_NOT_FOUND_MESSAGE, petName));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testExecute_WhenUserExistActivePet_ShouldSendStopMessage(boolean petIsActive) {
        String petName = "Charlie";
        String inputCommand = STOP.getCommandName() + " " + petName;

        Mockito.when(message.getText()).thenReturn(inputCommand);

        Pet mockPet = Mockito.mock(Pet.class);
        Mockito.when(mockPet.isActive()).thenReturn(petIsActive);

        Mockito.when(petService.findByNameIgnoreCaseAndChatId(petName, CHAT_ID))
                .thenReturn(Optional.of(mockPet));
        Mockito.when(petService.save(any(Pet.class)))
                .thenReturn(mockPet);
        Mockito.doNothing().when(petService).disable(mockPet);

        update.setMessage(message);

        StopPetCommand stopPetCommand = new StopPetCommand(sendBotMessageService, telegramUpdateService, petService);
        stopPetCommand.execute(update);

        if (petIsActive) {
            Mockito.verify(sendBotMessageService).sendMessage(CHAT_ID, String.format(STOP_MESSAGE, petName));
        } else {
            Mockito.verify(sendBotMessageService).sendMessage(CHAT_ID, String.format(PET_ALREADY_STOPPED_MESSAGE, petName));
        }

    }

}