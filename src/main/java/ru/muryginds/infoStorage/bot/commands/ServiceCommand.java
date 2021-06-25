package ru.muryginds.infoStorage.bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.muryginds.infoStorage.bot.utils.Utils;

abstract class ServiceCommand extends BotCommand {

  static final Logger logger = LoggerFactory.getLogger(ServiceCommand.class);

  ServiceCommand(String identifier, String description) {
    super(identifier, description);
  }

  void sendAnswer(AbsSender absSender, Long chatId, String commandName,
                  String userName, String text) {
    SendMessage message = Utils.prepareSendMessage(chatId, text);
    try {
      absSender.execute(message);
    } catch (TelegramApiException e) {
      StringBuilder info = new StringBuilder();
      info.append("Command: ").append(commandName).append(" User: ").append(userName);
      logger.error(info.toString(), e);
    }
  }
}