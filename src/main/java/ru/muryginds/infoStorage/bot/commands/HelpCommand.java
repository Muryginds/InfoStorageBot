package ru.muryginds.infoStorage.bot.commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.muryginds.infoStorage.bot.utils.Utils;


@Component("helpCommand")
@Order(100)
public class HelpCommand extends ServiceCommand {

  public HelpCommand(@Value("help") String identifier,
      @Value("Помощь") String description) {
    super(identifier, description);
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    String userName = Utils.getUserName(user);

    sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
        "Этот бот умеет хранить и выдавать информацию по тэгам.\n"
            + "Для того, чтобы начать его использовать отправьте ему сообщение,"
            + " начинающееся с хэштэга #");
  }
}