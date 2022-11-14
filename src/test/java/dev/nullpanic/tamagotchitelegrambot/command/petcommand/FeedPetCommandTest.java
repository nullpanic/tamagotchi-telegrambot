package dev.nullpanic.tamagotchitelegrambot.command.petcommand;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;
import dev.nullpanic.tamagotchitelegrambot.service.PetService;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUpdateService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUpdateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static dev.nullpanic.tamagotchitelegrambot.command.CommandName.FEED;
import static dev.nullpanic.tamagotchitelegrambot.command.petcommand.FeedPetCommand.*;

@DisplayName("Unit-level testing for FeedPetCommand")
public class FeedPetCommandTest {
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
        String inputCommand = FEED.getCommandName() + " " + petName;

        Mockito.when(message.getText()).thenReturn(inputCommand);

        update.setMessage(message);

        FeedPetCommand feedPetCommand = new FeedPetCommand(sendBotMessageService, petService, telegramUpdateService);
        feedPetCommand.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(CHAT_ID, PET_NAME_IS_BLANK_MESSAGE);
    }

    @Test
    public void testExecute_WhenPetNotFound_ShouldSendPetNotFoundMessage() {
        String petName = "invisible pet";
        String inputCommand = FEED.getCommandName() + " " + petName;

        Mockito.when(message.getText()).thenReturn(inputCommand);

        update.setMessage(message);

        FeedPetCommand feedPetCommand = new FeedPetCommand(sendBotMessageService, petService, telegramUpdateService);
        feedPetCommand.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(CHAT_ID, String.format(PET_NOT_FOUND_MESSAGE, petName));
    }

    @Test
    public void testExecute_WhenAllArgsCorrectlyFilled_ShouldSendFeedPetMessage() {
        Pet pet = new Pet();
        pet.setHungriness(50);
        pet.setName("Charlie");
        pet.setChatId(CHAT_ID);

        String inputCommand = FEED.getCommandName() + " " + pet.getName();
        Mockito.when(message.getText()).thenReturn(inputCommand);

        update.setMessage(message);

        Mockito.when(petService.findByNameAndChatId(pet.getName(), pet.getChatId())).thenReturn(Optional.of(pet));
        Mockito.when(petService.savePet(pet)).thenReturn(pet);
        Mockito.doNothing().when(petService).feedPet(pet);

        FeedPetCommand feedPetCommand = new FeedPetCommand(sendBotMessageService, petService, telegramUpdateService);
        feedPetCommand.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(pet.getChatId(), String.format(FEED_PET_MESSAGE, pet.getName(), pet.getHungriness()));
    }
}
