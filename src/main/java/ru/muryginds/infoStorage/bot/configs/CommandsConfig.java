package ru.muryginds.infoStorage.bot.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;

@Configuration
public class CommandsConfig {

  @Autowired
  @Qualifier("helpCommand")
  private BotCommand helpCommand;

  @Autowired
  @Qualifier("startCommand")
  private BotCommand startCommand;

  @Autowired
  @Qualifier("addCommand")
  private BotCommand addCommand;

  @Bean("myBotCommands")
  public BotCommand[] myBotCommands () {
    return new BotCommand[]{helpCommand, startCommand, addCommand};
  }
}