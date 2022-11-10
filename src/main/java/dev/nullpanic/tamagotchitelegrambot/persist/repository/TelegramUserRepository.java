package dev.nullpanic.tamagotchitelegrambot.persist.repository;

import dev.nullpanic.tamagotchitelegrambot.persist.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
    List<TelegramUser> findByActiveTrue();
}
