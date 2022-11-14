package dev.nullpanic.tamagotchitelegrambot.persist.repository;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByNameAndChatId(String name, Long chatId);
}
