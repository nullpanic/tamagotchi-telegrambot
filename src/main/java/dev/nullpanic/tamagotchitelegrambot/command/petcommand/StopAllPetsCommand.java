package dev.nullpanic.tamagotchitelegrambot.command.petcommand;

import dev.nullpanic.tamagotchitelegrambot.command.Command;
import dev.nullpanic.tamagotchitelegrambot.service.PetService;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUpdateService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopAllPetsCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUpdateService telegramUpdateService;
    private final PetService petService;

    public final static String STOP_MESSAGE = "Все питомцы заморожены в криокамерах, не забудь к ним вернутся! Увидимся! (:";
    public final static String NOT_FOUND_ACTIVE_PETS_MESSAGE = "Отсутствуют активные питомцы для заморозки!";

    public StopAllPetsCommand(SendBotMessageService sendBotMessageService, TelegramUpdateService telegramUpdateService, PetService petService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUpdateService = telegramUpdateService;
        this.petService = petService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = telegramUpdateService.getChatId(update);

        petService.findPetsByChatIdAndActive(chatId, true)
                .ifPresent(pets -> {
                    if (pets.isEmpty()) {
                        sendBotMessageService.sendMessage(chatId, NOT_FOUND_ACTIVE_PETS_MESSAGE);
                        return;
                    }
                    pets.forEach(pet -> {
                        pet.setActive(false);
                        petService.save(pet);
                    });
                    sendBotMessageService.sendMessage(chatId, STOP_MESSAGE);
                });


    }
}
