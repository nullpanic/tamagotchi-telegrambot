package dev.nullpanic.tamagotchitelegrambot.persist.repository;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByNameIgnoreCaseAndChatId(String name, Long chatId);

    Optional<List<Pet>> findPetsByChatId(Long chatId);

    @Query(value = """
            SELECT *
            FROM pets
                LEFT JOIN tg_user AS tg_users
                 on pets.chat_id = tg_users.chat_id
            WHERE
                tg_users.active
                and pets.hungriness < ?1""",
            nativeQuery = true)
    Optional<List<Pet>> findAllActiveHungrinessPets(int hungrinessThreshold);

    @Query(value = """
            SELECT *
            FROM pets
             LEFT JOIN tg_user tg_users on pets.chat_id = tg_users.chat_id
            WHERE tg_users.active
            """,
            nativeQuery = true)
    Optional<List<Pet>> findAllActivePets();
}
