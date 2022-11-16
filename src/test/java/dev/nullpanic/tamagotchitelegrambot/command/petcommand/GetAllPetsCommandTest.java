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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.nullpanic.tamagotchitelegrambot.command.CommandName.GET_ALL_PETS;
import static dev.nullpanic.tamagotchitelegrambot.command.petcommand.GetAllPetsCommand.PETS_NOT_FOUND_MESSAGE;

@DisplayName("Unit-level testing for GetAllPetsCommand")
class GetAllPetsCommandTest {
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
    public void testExecute_WhenUserHasNoPets_ShouldSendPetsNotFoundMessage() {
        List<Pet> pets = new ArrayList<>();

        Mockito.when(message.getText()).thenReturn(GET_ALL_PETS.getCommandName());
        Mockito.when(petService.findPetsByChatId(CHAT_ID)).thenReturn(Optional.of(pets));

        update.setMessage(message);

        GetAllPetsCommand getAllPetsCommand = new GetAllPetsCommand(sendBotMessageService, petService, telegramUpdateService);
        getAllPetsCommand.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(CHAT_ID, PETS_NOT_FOUND_MESSAGE);
    }

    @Test
    public void testExecute_WhenUserHasPets_ShouldSendListOfUserPets() {
        List<Pet> pets = new ArrayList<>();

        pets.add(
                Pet.builder()
                        .petId(1L)
                        .name("Charlie")
                        .chatId(CHAT_ID)
                        .active(true)
                        .build()
        );
        pets.add(
                Pet.builder()
                        .petId(2L)
                        .name("Sad pet")
                        .chatId(CHAT_ID)
                        .active(true)
                        .build()
        );
        pets.add(
                Pet.builder()
                        .petId(3L)
                        .name("   Sad       pet ")
                        .chatId(CHAT_ID)
                        .active(true)
                        .build()
        );

        StringBuilder stringBuilder = new StringBuilder();
        pets.forEach(pet -> stringBuilder.append(pet.getStatusString()));

        Mockito.when(message.getText()).thenReturn(GET_ALL_PETS.getCommandName());
        Mockito.when(petService.findPetsByChatId(CHAT_ID)).thenReturn(Optional.of(pets));

        update.setMessage(message);

        GetAllPetsCommand getAllPetsCommand = new GetAllPetsCommand(sendBotMessageService, petService, telegramUpdateService);
        getAllPetsCommand.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(CHAT_ID, stringBuilder.toString());
    }


}