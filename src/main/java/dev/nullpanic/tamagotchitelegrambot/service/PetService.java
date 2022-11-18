package dev.nullpanic.tamagotchitelegrambot.service;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {

    Pet create(Long chatId, String petName);

    Pet save(Pet pet);

    void feed(Pet pet);

    Optional<Pet> findByNameIgnoreCaseAndChatId(String name, Long chatId);

    Optional<List<Pet>> findPetsByChatId(Long chatId);

    Optional<List<Pet>> findLowHungrinessPets(int HungrinessThreshold);
}
