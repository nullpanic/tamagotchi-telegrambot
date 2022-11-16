package dev.nullpanic.tamagotchitelegrambot.command.petcommand;

import dev.nullpanic.tamagotchitelegrambot.command.Command;
import dev.nullpanic.tamagotchitelegrambot.service.PetService;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

public class GetAllPetsCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final PetService petService;
    private final TelegramUpdateService telegramUpdateService;
    public static final String PETS_NOT_FOUND_MESSAGE = "У вас нет ни одного питомца :(";

    public GetAllPetsCommand(SendBotMessageService sendBotMessageService, PetService petService, TelegramUpdateService telegramUpdateService) {
        this.sendBotMessageService = sendBotMessageService;
        this.petService = petService;
        this.telegramUpdateService = telegramUpdateService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = telegramUpdateService.getChatId(update);

        petService.findPetsByChatId(chatId)
                .ifPresent(pets -> {
                    if (pets.isEmpty()) {
                        sendBotMessageService.sendMessage(chatId, PETS_NOT_FOUND_MESSAGE);
                        return;
                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    pets.forEach(pet -> stringBuilder.append(pet.getStatusString()));
                    sendBotMessageService.sendMessage(chatId, stringBuilder.toString());
                });
    }
}
