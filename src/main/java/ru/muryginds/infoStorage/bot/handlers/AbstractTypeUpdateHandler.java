package ru.muryginds.infoStorage.bot.handlers;

import java.util.List;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

interface AbstractTypeUpdateHandler {

  List<BotApiMethod<?>> formAnswerList(Update update);

  default BotApiMethod<?> setAnswer(Long chatId, String text) {
    SendMessage answer = new SendMessage();
    answer.setText(text);
    answer.setChatId(chatId.toString());

    return answer;
  }
}