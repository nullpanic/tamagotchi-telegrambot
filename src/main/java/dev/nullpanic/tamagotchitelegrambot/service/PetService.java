package dev.nullpanic.tamagotchitelegrambot.service;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {

    Pet createPet(Long chatId, String petName);

    Pet savePet(Pet pet);

    void feedPet(Pet pet);

    Optional<Pet> findByNameIgnoreCaseAndChatId(String name, Long chatId);

    Optional<List<Pet>> findPetsByChatId(Long chatId);

    Optional<List<Pet>> findPetsByActive(boolean active);

    Optional<List<Pet>> findLowHpPets();

    Optional<List<Pet>> findLowHungrinessPets();
}
