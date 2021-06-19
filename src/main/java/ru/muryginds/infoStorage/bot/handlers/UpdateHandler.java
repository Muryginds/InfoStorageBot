package ru.muryginds.infoStorage.bot.handlers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
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

  public List<BotApiMethod<?>> handleUpdate(Update update) {

    List<BotApiMethod<?>> result = new ArrayList<>();

    if(update.hasCallbackQuery()) {
      result = callbackQueryUpdateHandler.formAnswerList(update);
    } else if(update.hasMessage()) {
      if (update.getMessage().hasText()) {
         result = messageUpdateHandler.formAnswerList(update);
      }
    }
    return result;
  }
}