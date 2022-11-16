package dev.nullpanic.tamagotchitelegrambot.persist.repository;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByNameIgnoreCaseAndChatId(String name, Long chatId);

    Optional<List<Pet>> findPetsByChatId(Long chatId);

    Optional<List<Pet>> findPetsByActive(boolean active);

    Optional<List<Pet>> findPetsByActiveAndHpBeforeOrderByChatId(boolean active, int hpThreshold);

    Optional<List<Pet>> findPetsByActiveAndHungrinessBeforeOrderByChatId(boolean active, int hungrinessThreshold);

    Optional<List<Pet>> findPetsByChatIdAndActive(Long chatId, boolean active);
}
