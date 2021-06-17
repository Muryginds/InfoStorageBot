package ru.muryginds.infoStorage.bot.commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;


@Component("startCommand")
@Order(100)
public class StartCommand extends ServiceCommand {

  public StartCommand(@Value("start") String identifier,
      @Value("Старт") String description) {
    super(identifier, description);
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
    //формируем имя пользователя - поскольку userName может быть не заполнено, для этого случая используем имя и фамилию пользователя
    String userName = (user.getUserName() != null) ? user.getUserName() :
        String.format("%s %s", user.getLastName(), user.getFirstName());
    //обращаемся к методу суперкласса для отправки пользователю ответа
    sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
        "Давайте начнём! Если Вам нужна помощь, нажмите /help");
  }
}
