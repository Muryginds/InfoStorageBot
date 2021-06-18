package ru.muryginds.infoStorage.bot.handlers;

import java.util.Optional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("callbackQueryUpdateHandler")
@Order(100)
public class CallbackQueryUpdateHandler implements AbstractTypeUpdateHandler {

  @Override
  public Optional<SendMessage> formAnswer(Update update) {

    Message msg = update.getCallbackQuery().getMessage();
    long chatId = msg.getChatId();
    String answer = update.getCallbackQuery().getData();

    return Optional.of(setAnswer(chatId, answer));
  }
}