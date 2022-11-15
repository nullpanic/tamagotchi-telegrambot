package dev.nullpanic.tamagotchitelegrambot.service;

import dev.nullpanic.tamagotchitelegrambot.bot.TamagotchiTelegramBot;
import dev.nullpanic.tamagotchitelegrambot.service.impl.SendBotMessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
class SendBotMessageServiceImplTest {

    private SendBotMessageService sendBotMessageService;
    private TamagotchiTelegramBot tamagotchiTelegramBot;

    @BeforeEach
    public void init() {
        tamagotchiTelegramBot = Mockito.mock(TamagotchiTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(tamagotchiTelegramBot);
    }

    @Test
    public void testSendMessage_WhenArgsFilled_ShouldInvokeTamagotchiTelegramBotExecuteWithArgSendMessage() throws TelegramApiException {
        Long chatId = 1L;
        String message = "Test message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        sendBotMessageService.sendMessage(chatId, message);

        Mockito.verify(tamagotchiTelegramBot).execute(sendMessage);
    }
}
