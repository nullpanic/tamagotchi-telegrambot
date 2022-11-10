package dev.nullpanic.tamagotchitelegrambot.command;

import dev.nullpanic.tamagotchitelegrambot.bot.TamagotchiTelegramBot;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageService;
import dev.nullpanic.tamagotchitelegrambot.service.SendBotMessageServiceImpl;
import dev.nullpanic.tamagotchitelegrambot.service.TelegramUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

abstract class AbstractCommandTest {

    protected TamagotchiTelegramBot tamagotchiTelegramBot = Mockito.mock(TamagotchiTelegramBot.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(tamagotchiTelegramBot);
    protected TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract Command getCommand();

    @Test
    public void testCommand_WhenInvokeExecute_ShouldInvokeTamagotchiTelegramBotExecuteWithArgSendMessage() throws TelegramApiException {
        Long chatId = 1L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);

        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());

        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(getCommandMessage());

        getCommand().execute(update);

        Mockito.verify(tamagotchiTelegramBot).execute(sendMessage);
    }
}
