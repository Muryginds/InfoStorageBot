package ru.muryginds.infoStorage.bot.handlers;

import java.util.Optional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

interface AbstractTypeUpdateHandler {

  Optional<SendMessage> formAnswer(Update update);

  default SendMessage setAnswer(Long chatId, String text) {
    SendMessage answer = new SendMessage();
    answer.setText(text);
    answer.setChatId(chatId.toString());

    return answer;
  }
}