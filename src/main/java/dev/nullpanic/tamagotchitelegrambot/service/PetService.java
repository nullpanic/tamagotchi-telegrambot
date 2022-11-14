package dev.nullpanic.tamagotchitelegrambot.service;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;

import java.util.Optional;

public interface PetService {

    Pet createPet(Long chatId, String petName);

    Pet savePet(Pet pet);

    void feedPet(Pet pet);

    Optional<Pet> findByNameAndChatId(String name, Long ChatId);
}
