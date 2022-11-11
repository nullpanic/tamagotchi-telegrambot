package dev.nullpanic.tamagotchitelegrambot.service;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;
import dev.nullpanic.tamagotchitelegrambot.persist.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }
}
