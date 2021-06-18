package ru.muryginds.infoStorage.bot.handlers;

import static ru.muryginds.infoStorage.bot.keyBoard.SendInlineKeyBoard.sendInlineKeyBoardMessage;

import java.util.Optional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("messageUpdateHandler")
@Order(100)
public class MessageUpdateHandler implements AbstractTypeUpdateHandler {

  @Override
  public Optional<SendMessage> formAnswer(Update update) {

    Message msg = update.getMessage();
    long chatId = msg.getChatId();

    if (update.getMessage().getText().equals("Hello")) {
      SendMessage resMessage =
          sendInlineKeyBoardMessage(chatId, msg.getMessageId());
      return Optional.of(resMessage);
    }

    return Optional.of(setAnswer(chatId, msg.getText()));
  }
}