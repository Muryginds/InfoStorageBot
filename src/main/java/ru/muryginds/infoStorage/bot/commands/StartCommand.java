package ru.muryginds.infoStorage.bot.commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.muryginds.infoStorage.bot.utils.Constants;
import ru.muryginds.infoStorage.bot.utils.Utils;


@Component("startCommand")
@Order(100)
public class StartCommand extends ServiceCommand {

  public StartCommand(@Value("start") String identifier,
      @Value("Start") String description) {
    super(identifier, description);
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    String userName = Utils.getUserName(user);

    sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, Constants.BOT_START);
  }
}