package ru.muryginds.infoStorage.bot.handlers;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.muryginds.infoStorage.bot.enums.BotState;
import ru.muryginds.infoStorage.bot.models.User;

public interface AbstractHandler {

  List<BotApiMethod<?>> getAnswerList(User user, BotApiObject botApiObject);

  BotState getOperatedBotState();

  default List<String> getOperatedCallBackQuery() {
    return new ArrayList<>();
  }
}