package ru.muryginds.infoStorage.bot;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.muryginds.infoStorage.bot.handlers.UpdateHandler;
import ru.muryginds.infoStorage.bot.utils.Utils;

@Component()
@Order(200)
public class Bot extends TelegramLongPollingCommandBot {

  @Getter
  private final String botUsername;
  @Getter
  private final String botToken;

  @Autowired
  @Qualifier("updateHandler")
  private UpdateHandler updateHandler;

  @Autowired
  @Qualifier("myBotCommands")
  private BotCommand[] myBotCommands;

  public Bot (@Value("${bot.name}") String userName,
      @Value("${bot.token}") String botToken) {
    super();
    this.botUsername = userName;
    this.botToken = botToken;
  }

  @PostConstruct
  public void init() {
    registerAll(myBotCommands);
  }

  @Override
  public void processNonCommandUpdate(Update update) {
    List<BotApiMethod<?>> botApiMethodList
        = updateHandler.handleUpdate(update);
     try {
       for (BotApiMethod<?> botApiMethod: botApiMethodList) {
         execute(botApiMethod);
       }
     } catch (TelegramApiException e) {
       String userName = Utils.getUserName(update.getMessage().getFrom());
       //логируем сбой Telegram Bot API, используя userName
     }
  }

}