package dev.nullpanic.tamagotchitelegrambot.service;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;
import dev.nullpanic.tamagotchitelegrambot.persist.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {

    private final int HungrinessIncrement = 20;
    private final PetRepository petRepository;

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
        int newHungriness = pet.getHungriness() + HungrinessIncrement;

        if (newHungriness > 100) {
            newHungriness = 100;
        }

        pet.setHungriness(newHungriness);
    }

    @Override
    public Optional<Pet> findByNameAndChatId(String name, Long chatId) {
        return petRepository.findByNameAndChatId(name, chatId);
    }
}
