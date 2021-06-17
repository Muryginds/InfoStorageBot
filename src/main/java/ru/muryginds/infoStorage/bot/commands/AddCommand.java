package ru.muryginds.infoStorage.bot.commands;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.muryginds.infoStorage.bot.utils.Utils;


@Component("addCommand")
@Order(100)
public class AddCommand extends ServiceCommand {

  public AddCommand(@Value("add") String identifier,
      @Value("Addition") String description) {
    super(identifier, description);
  }

  @Override
  public void processMessage(AbsSender absSender, Message message, String[] arguments) {
    if (message.isReply()) {

      List<String> tags = new ArrayList<>();

      for (String string : arguments) {
        if (string.startsWith("$")) {
          tags.add(string);
        } else {
          break;
        }
      }

      String userName = Utils.getUserName(message.getFrom());
      sendAnswer(absSender, message.getChat().getId(), this.getCommandIdentifier(), userName,
          "You are trying to add info with: " + tags + " mesID: "
              + message.getReplyToMessage().getMessageId());
    } else {
      super.processMessage(absSender, message, arguments);
    }
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    List<String> tags = new ArrayList<>();

    for (String string : strings) {
      if (string.startsWith("$")) {
        tags.add(string);
      } else {
        break;
      }
    }

    String userName = Utils.getUserName(user);

    sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
        "You are trying to add info"
            + " with: " + tags);
  }
}