package ru.muryginds.infoStorage.bot.handlers;

import static ru.muryginds.infoStorage.bot.handlers.keyboards.SendInlineKeyBoard.sendInlineKeyboardMessage;

import java.util.ArrayList;
import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("messageUpdateHandler")
@Order(100)
public class MessageUpdateHandler implements AbstractTypeUpdateHandler {

  @Override
  public List<BotApiMethod<?>> formAnswerList(Update update) {

    List<BotApiMethod<?>> answer = new ArrayList<>();
    Message message = update.getMessage();
    long chatId = message.getChatId();

    if (update.getMessage().getText().equals("Hello")) {
      SendMessage resMessage =
          sendInlineKeyboardMessage(chatId, message.getMessageId());
      answer.add(resMessage);
    } else {
      answer.add(setAnswer(chatId, message.getText()));
    }

    return answer;
  }
}