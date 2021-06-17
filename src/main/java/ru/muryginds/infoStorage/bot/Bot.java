package ru.muryginds.infoStorage.bot;

import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.muryginds.infoStorage.bot.commands.NonCommand;
import ru.muryginds.infoStorage.bot.commands.StartCommand;

@Component()
@Order(200)
public class Bot extends TelegramLongPollingCommandBot {

  @Getter
  private final String botUsername;
  @Getter
  private final String botToken;

  @Autowired
  @Qualifier("nonCommand")
  private NonCommand nonCommand;

  @Autowired
  @Qualifier("startCommand")
  StartCommand startCommand;

  public Bot (@Value("${bot.name}") String userName,
      @Value("${bot.token}") String botToken) {
    super();
    this.botUsername = userName;
    this.botToken = botToken;
  }

  @PostConstruct
  public void init() {
    register(startCommand);
  }

  @Override
  public void processNonCommandUpdate(Update update) {
    Message msg = update.getMessage();
    Long chatId = msg.getChatId();
    String userName = getUserName(msg);

    String answer = nonCommand.nonCommandExecute(chatId, userName, msg.getText());
    setAnswer(chatId, userName, answer);
  }

  private String getUserName(Message msg) {
    User user = msg.getFrom();
    String userName = user.getUserName();
    return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
  }

  private void setAnswer(Long chatId, String userName, String text) {
    SendMessage answer = new SendMessage();
    answer.setText(text);
    answer.setChatId(chatId.toString());
    try {
      execute(answer);
    } catch (TelegramApiException e) {
      //логируем сбой Telegram Bot API, используя userName
    }
  }
}
