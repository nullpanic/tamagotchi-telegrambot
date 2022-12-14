package dev.nullpanic.tamagotchitelegrambot.service.impl;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;
import dev.nullpanic.tamagotchitelegrambot.persist.repository.PetRepository;
import dev.nullpanic.tamagotchitelegrambot.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Pet create(Long chatId, String petName) {
        Pet pet = new Pet();
        pet.setChatId(chatId);
        pet.setName(petName);
        pet.setDefaultStats();

        return petRepository.save(pet);
    }

    @Override
    @Transactional
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public void feed(Pet pet) {
        int newHungriness = pet.getHungriness() + hungrinessIncrement;

        if (newHungriness > petMaxHp) {
            newHungriness = petMaxHp;
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
    public Optional<List<Pet>> findLowHungrinessPets(int HungrinessThreshold) {
        return petRepository.findAllActiveHungrinessPets(hungrinessThreshold);
    }

}
