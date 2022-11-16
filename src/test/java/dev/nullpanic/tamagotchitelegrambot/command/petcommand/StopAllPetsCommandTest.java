package dev.nullpanic.tamagotchitelegrambot.command.petcommand;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;
import dev.nullpanic.tamagotchitelegrambot.service.PetService;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUpdateService;
import dev.nullpanic.tamagotchitelegrambot.service.impl.TelegramUpdateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.*;

import static dev.nullpanic.tamagotchitelegrambot.command.petcommand.StopAllPetsCommand.NOT_FOUND_ACTIVE_PETS_MESSAGE;
import static dev.nullpanic.tamagotchitelegrambot.command.petcommand.StopAllPetsCommand.STOP_MESSAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;

@DisplayName("Unit-level testing for StopAllCommand")
class StopAllPetsCommandTest {
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

    @Test
    public void testExecute_WhenUserExistActivePets_ShouldSendStopMessage() {
        Pet mockPet = Mockito.mock(Pet.class);
        Mockito.doNothing().when(mockPet).setActive(anyBoolean());

        List<Pet> pets = Collections.singletonList(mockPet);

        Mockito.when(petService.findPetsByChatIdAndActive(CHAT_ID, true))
                .thenReturn(Optional.of(pets));
        Mockito.when(petService.save(any(Pet.class)))
                .thenReturn(mockPet);

        update.setMessage(message);

        StopAllPetsCommand stopAllPetsCommand = new StopAllPetsCommand(sendBotMessageService, telegramUpdateService, petService);
        stopAllPetsCommand.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(CHAT_ID, STOP_MESSAGE);
    }

    @Test
    public void testExecute_WhenUserNotExistActivePets_ShouldSendNotFoundActivePetsMessage() {
        List<Pet> pets = new ArrayList<>();

        Mockito.when(petService.findPetsByChatIdAndActive(CHAT_ID, true))
                .thenReturn(Optional.of(pets));

        update.setMessage(message);

        StopAllPetsCommand stopAllPetsCommand = new StopAllPetsCommand(sendBotMessageService, telegramUpdateService, petService);
        stopAllPetsCommand.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(CHAT_ID, NOT_FOUND_ACTIVE_PETS_MESSAGE);
    }
}