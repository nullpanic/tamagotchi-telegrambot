package dev.nullpanic.tamagotchitelegrambot.command.petcommand;

import dev.nullpanic.tamagotchitelegrambot.command.Command;
import dev.nullpanic.tamagotchitelegrambot.service.PetService;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

public class FeedPetCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final PetService petService;
    private final TelegramUpdateService telegramUpdateService;

    public static final String FEED_PET_MESSAGE = "Вы покормили %s, текущий уровень сытости - %s";
    public static final String PET_NAME_IS_BLANK_MESSAGE = "Не указано имя питомца";
    public static final String PET_NOT_FOUND_MESSAGE = "Питомец с именем %s не найден";


    @Autowired
    public FeedPetCommand(SendBotMessageService sendBotMessageService, PetService petService, TelegramUpdateService telegramUpdateService) {
        this.sendBotMessageService = sendBotMessageService;
        this.petService = petService;
        this.telegramUpdateService = telegramUpdateService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = telegramUpdateService.getChatId(update);
        String[] splitCommand = telegramUpdateService.getSplitCommand(update, "/feed ");

        if (splitCommand.length != 2) {
            sendPetNameIsBlankMessage(chatId);
            return;
        }

        String petName = splitCommand[1];

        if (petName.isBlank()) {
            sendPetNameIsBlankMessage(chatId);
            return;
        }

        petService.findByNameIgnoreCaseAndChatId(petName, chatId)
                .ifPresentOrElse(
                        pet -> {
                            petService.feedPet(pet);
                            petService.savePet(pet);
                            sendBotMessageService.sendMessage(chatId, String.format(FEED_PET_MESSAGE, petName, pet.getHungriness()));
                        }, () -> sendPetNotFoundMessage(chatId, petName)
                );
    }

    private void sendPetNameIsBlankMessage(Long chatId) {
        sendBotMessageService.sendMessage(chatId, PET_NAME_IS_BLANK_MESSAGE);
    }

    private void sendPetNotFoundMessage(Long chatId, String petName) {
        sendBotMessageService.sendMessage(chatId, String.format(PET_NOT_FOUND_MESSAGE, petName));
    }
}
