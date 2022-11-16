package dev.nullpanic.tamagotchitelegrambot.service;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {

    Pet create(Long chatId, String petName);

    Pet save(Pet pet);

    void feed(Pet pet);

    void disable(Pet pet);

    Optional<Pet> findByNameIgnoreCaseAndChatId(String name, Long chatId);

    Optional<List<Pet>> findPetsByChatId(Long chatId);

    Optional<List<Pet>> findPetsByChatIdAndActive(Long chatId, boolean active);

    Optional<List<Pet>> findPetsByActive(boolean active);

    Optional<List<Pet>> findLowHpPets();

    Optional<List<Pet>> findLowHungrinessPets();
}
