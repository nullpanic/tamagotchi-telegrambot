package dev.nullpanic.tamagotchitelegrambot.command.petcommand;

import dev.nullpanic.tamagotchitelegrambot.command.Command;
import dev.nullpanic.tamagotchitelegrambot.service.PetService;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUpdateService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopPetCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUpdateService telegramUpdateService;
    private final PetService petService;

    public final static String STOP_MESSAGE = "%s заморожен в криокамере, не забудь к нему вернутся! Увидимся! (:";
    public final static String PET_ALREADY_STOPPED_MESSAGE = "%s уже заморожен!";
    public static final String PET_NAME_IS_BLANK_MESSAGE = "Не указано имя питомца";
    public static final String PET_NOT_FOUND_MESSAGE = "Питомец с именем %s не найден";

    public StopPetCommand(SendBotMessageService sendBotMessageService, TelegramUpdateService telegramUpdateService, PetService petService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUpdateService = telegramUpdateService;
        this.petService = petService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = telegramUpdateService.getChatId(update);
        String[] splitCommand = telegramUpdateService.getSplitCommand(update, "/stop ");

        if (splitCommand.length != 2) {
            sendBotMessageService.sendMessage(chatId, PET_NAME_IS_BLANK_MESSAGE);
            return;
        }

        String petName = splitCommand[1];

        if (petName.isBlank()) {
            sendBotMessageService.sendMessage(chatId, PET_NAME_IS_BLANK_MESSAGE);
            return;
        }

        petService.findByNameIgnoreCaseAndChatId(petName, chatId)
                .ifPresentOrElse(
                        pet -> {
                            if (!pet.isActive()) {
                                sendBotMessageService.sendMessage(chatId, String.format(PET_ALREADY_STOPPED_MESSAGE, petName));
                                return;
                            }
                            petService.disable(pet);
                            petService.save(pet);
                            sendBotMessageService.sendMessage(chatId, String.format(STOP_MESSAGE, petName));
                        }, () -> sendBotMessageService.sendMessage(chatId, String.format(PET_NOT_FOUND_MESSAGE, petName))
                );
    }
}
