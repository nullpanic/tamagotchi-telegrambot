package dev.nullpanic.tamagotchitelegrambot.command.petcommand;

import dev.nullpanic.tamagotchitelegrambot.command.Command;
import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;
import dev.nullpanic.tamagotchitelegrambot.service.PetService;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CreatePetCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final PetService petService;
    private final TelegramUpdateService telegramUpdateService;
    public final static String CREATE_PET_MESSAGE = """
            УРА! На свет появился новый питомец:
                        
            Имя - %s
            Здоровье - %s
            Сытость - %s
            Усталость - %s           
            Счастье - %s
            Чистота - %s""";

    public static final String PET_NAME_IS_BLANK_MESSAGE = "Не указано имя питомца";
    public final static String PET_ALREADY_EXIST_MESSAGE = "Питомец с именем %s уже существует";

    @Autowired
    public CreatePetCommand(SendBotMessageService sendBotMessageService, PetService petService, TelegramUpdateService telegramUpdateService) {
        this.sendBotMessageService = sendBotMessageService;
        this.petService = petService;
        this.telegramUpdateService = telegramUpdateService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = telegramUpdateService.getChatId(update);
        String[] splitCommand = telegramUpdateService.getSplitCommand(update, "/create ");

        if (splitCommand.length != 2) {
            sendBotMessageService.sendMessage(chatId, PET_NAME_IS_BLANK_MESSAGE);
            return;
        }

        String petName = telegramUpdateService.removeExtraSpaces(splitCommand[1]);

        petService.findByNameIgnoreCaseAndChatId(petName, chatId).ifPresentOrElse(
                pet -> sendBotMessageService.sendMessage(chatId, String.format(PET_ALREADY_EXIST_MESSAGE, petName))
                , () -> {
                    Pet pet = petService.create(chatId, petName);
                    sendCreatePetMessage(chatId, pet);
                }
        );
    }

    private void sendCreatePetMessage(Long chatId, Pet pet) {
        sendBotMessageService.sendMessage(chatId, String.format(CREATE_PET_MESSAGE,
                pet.getName(),
                pet.getHp(),
                pet.getHungriness(),
                pet.getTiredness(),
                pet.getHappiness(),
                pet.getCleanliness()));
    }
}
