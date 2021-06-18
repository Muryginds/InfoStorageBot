package ru.muryginds.infoStorage.bot.handlers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("updateHandler")
@Order(150)
public class UpdateHandler {

  @Autowired
  @Qualifier("callbackQueryUpdateHandler")
  CallbackQueryUpdateHandler callbackQueryUpdateHandler;

  @Autowired
  @Qualifier("messageUpdateHandler")
  MessageUpdateHandler messageUpdateHandler;

  public Optional<SendMessage> handleUpdate(Update update) {

    Optional<SendMessage> result = Optional.empty();

    if(update.hasCallbackQuery()) {
      result = callbackQueryUpdateHandler.formAnswer(update);
    } else if(update.hasMessage()) {
      if (update.getMessage().hasText()) {
         result = messageUpdateHandler.formAnswer(update);
      }
    }
    return result;
  }
}