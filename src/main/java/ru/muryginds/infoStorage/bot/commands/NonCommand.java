package ru.muryginds.infoStorage.bot.commands;

import org.springframework.stereotype.Component;

@Component("nonCommand")
public class NonCommand {
  public String nonCommandExecute(Long chatId, String userName,
      String text) {
    return "Получено сообщение без команды: " + text;
  }
}
