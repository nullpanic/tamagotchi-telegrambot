package dev.nullpanic.tamagotchitelegrambot.service.impl;

import dev.nullpanic.tamagotchitelegrambot.persist.model.TelegramUser;
import dev.nullpanic.tamagotchitelegrambot.persist.repository.TelegramUserRepository;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelegramUserServiceImpl implements TelegramUserService {

    private final TelegramUserRepository telegramUserRepository;

    @Autowired
    public TelegramUserServiceImpl(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    @Override
    public void save(TelegramUser telegramUser) {
        telegramUserRepository.save(telegramUser);
    }

    @Override
    public List<TelegramUser> retrieveAllActiveUsers() {
        return telegramUserRepository.findByActiveTrue();
    }

    @Override
    public Optional<TelegramUser> findByChatId(Long chatId) {
        return telegramUserRepository.findById(chatId);
    }
}
