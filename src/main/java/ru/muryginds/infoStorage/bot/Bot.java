package ru.muryginds.infoStorage.bot;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.annotation.PostConstruct;
import java.util.List;

@Component()
@Order(200)
public class Bot extends TelegramLongPollingCommandBot {

  private static final Logger logger = LoggerFactory.getLogger(Bot.class);

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
       logger.error("Error while executing message for: " + userName, e);
     }
  }
}