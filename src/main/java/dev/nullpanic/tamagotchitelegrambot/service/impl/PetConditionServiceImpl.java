package dev.nullpanic.tamagotchitelegrambot.service.impl;

import dev.nullpanic.tamagotchitelegrambot.persist.repository.PetRepository;
import dev.nullpanic.tamagotchitelegrambot.service.PetConditionService;
import dev.nullpanic.tamagotchitelegrambot.service.PetService;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PetConditionServiceImpl implements PetConditionService {

    private final PetRepository petRepository;
    private final PetService petService;
    private final SendBotMessageService sendBotMessageService;
    @Value("${pet.reduce.hungriness}")
    private int reduceHungrinessPerIteration;
    private final String LOW_HP_PET_MESSAGE = "Скорее покорми \"%s\", его сытость уже %s";

    @Autowired
    public PetConditionServiceImpl(PetRepository petRepository, PetService petService, SendBotMessageService sendBotMessageService) {
        this.petRepository = petRepository;
        this.petService = petService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void decreasePetsStatus() {
        petService.findPetsByActive(true)
                .ifPresent(pets -> {
                    pets.forEach(pet -> {
                        int newHungriness = pet.getHungriness() - reduceHungrinessPerIteration;
                        pet.setHungriness(Math.max(newHungriness, 0));
                        petRepository.save(pet);
                    });
                });
    }

    @Override
    public void sendLowHungrinessPetNotifications() {
        petService.findLowHungrinessPets().ifPresent(pets -> {
            pets.forEach(
                    pet -> sendBotMessageService.sendMessage(
                            pet.getChatId(), String.format(LOW_HP_PET_MESSAGE, pet.getName(), pet.getHungriness())
                    )
            );
        });
    }
}
