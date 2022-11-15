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

import java.util.Optional;

import static dev.nullpanic.tamagotchitelegrambot.command.CommandName.CREATE;
import static dev.nullpanic.tamagotchitelegrambot.command.petcommand.CreatePetCommand.CREATE_PET_MESSAGE;
import static dev.nullpanic.tamagotchitelegrambot.command.petcommand.FeedPetCommand.PET_NAME_IS_BLANK_MESSAGE;

@DisplayName("Unit-level testing for CreatePetCommandTest")
class CreatePetCommandTest {
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
    public void testExecute_WhenPetNameIsBlank_ShouldSendPetNameIsBlankMessage() {
        String petName = "     ";
        String inputCommand = CREATE.getCommandName() + " " + petName;

        Mockito.when(message.getText()).thenReturn(inputCommand);

        update.setMessage(message);

        FeedPetCommand feedPetCommand = new FeedPetCommand(sendBotMessageService, petService, telegramUpdateService);
        feedPetCommand.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(CHAT_ID, PET_NAME_IS_BLANK_MESSAGE);
    }

    @Test
    public void testExecute_WhenPetNotExist_ShouldSendFeedCreateMessage() {
        Pet pet = new Pet();
        pet.setName("NotExistingPet");
        pet.setChatId(CHAT_ID);
        pet.setActive(true);
        pet.setDefaultStats();

        Mockito.when(petService.findByNameIgnoreCaseAndChatId(pet.getName(), pet.getChatId()))
                .thenReturn(Optional.empty());
        Mockito.when(petService.createPet(pet.getChatId(), pet.getName()))
                .thenReturn(pet);

        String inputCommand = CREATE.getCommandName() + " " + pet.getName();
        Mockito.when(message.getText()).thenReturn(inputCommand);

        update.setMessage(message);

        CreatePetCommand createPetCommand = new CreatePetCommand(sendBotMessageService, petService, telegramUpdateService);
        createPetCommand.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(pet.getChatId(), String.format(CREATE_PET_MESSAGE,
                pet.getName(),
                pet.getHp(),
                pet.getHungriness(),
                pet.getCleanliness(),
                pet.getHappiness(),
                pet.getTiredness()));
    }

}