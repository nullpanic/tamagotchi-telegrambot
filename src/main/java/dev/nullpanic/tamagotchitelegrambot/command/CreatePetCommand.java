package dev.nullpanic.tamagotchitelegrambot.command;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;
import dev.nullpanic.tamagotchitelegrambot.service.PetService;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CreatePetCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final PetService petService;
    public final static String CREATE_PET_MESSAGE = """
            УРА! На свет появился новый питомец:
                        
            Имя - %s
            Здоровье - %s
            Сытость - %s
            Чистота - %s
            Счастье - %s
            Усталость - %s""";

    @Autowired
    public CreatePetCommand(SendBotMessageService sendBotMessageService, PetService petService) {
        this.sendBotMessageService = sendBotMessageService;
        this.petService = petService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        String[] split = update.getMessage().getText().split("/create ");

        if (split.length != 2) {
            sendBotMessageService.sendMessage(chatId, "После комманды нужно ввести имя питомца. Пример: \"/create Пушок\"");
            return;
        }

        String petName = split[1];

        Pet pet = new Pet();
        pet.setChatId(chatId);
        pet.setActive(true);
        pet.setName(petName);
        pet.setHp(100);
        pet.setCleanliness(100);
        pet.setHappiness(100);
        pet.setHungriness(100);
        pet.setTiredness(100);

        petService.createPet(pet);

        sendBotMessageService.sendMessage(
                chatId,
                String.format(CREATE_PET_MESSAGE,
                        pet.getName(),
                        pet.getHp(),
                        pet.getHungriness(),
                        pet.getCleanliness(),
                        pet.getHappiness(),
                        pet.getTiredness()));
    }
}
