package dev.nullpanic.tamagotchitelegrambot.service.impl;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;
import dev.nullpanic.tamagotchitelegrambot.persist.repository.PetRepository;
import dev.nullpanic.tamagotchitelegrambot.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    @Value("${pet.threshold.hp}")
    private int hpThreshold;
    @Value("${pet.threshold.hungriness}")
    private int hungrinessThreshold;
    @Value("${pet.command.feed}")
    private int hungrinessIncrement;
    @Value("${pet.stats.maxHp}")
    private int petMaxHp;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(Long chatId, String petName) {
        Pet pet = new Pet();
        pet.setChatId(chatId);
        pet.setActive(true);
        pet.setName(petName);
        pet.setDefaultStats();

        return petRepository.save(pet);
    }

    @Override
    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public void feedPet(Pet pet) {
        int newHungriness = pet.getHungriness() + hungrinessIncrement;

        if (newHungriness > petMaxHp) {
            newHungriness = 100;
        }

        pet.setHungriness(newHungriness);
    }

    @Override
    public Optional<Pet> findByNameIgnoreCaseAndChatId(String name, Long chatId) {
        return petRepository.findByNameIgnoreCaseAndChatId(name, chatId);
    }

    @Override
    public Optional<List<Pet>> findPetsByChatId(Long chatId) {
        return petRepository.findPetsByChatId(chatId);
    }

    @Override
    public Optional<List<Pet>> findPetsByActive(boolean active) {
        return petRepository.findPetsByActive(active);
    }

    @Override
    public Optional<List<Pet>> findLowHpPets() {
        return petRepository.findPetsByActiveAndHpBeforeOrderByChatId(true, hpThreshold);
    }

    @Override
    public Optional<List<Pet>> findLowHungrinessPets() {
        return petRepository.findPetsByActiveAndHungrinessBeforeOrderByChatId(true, hungrinessThreshold);
    }
}
