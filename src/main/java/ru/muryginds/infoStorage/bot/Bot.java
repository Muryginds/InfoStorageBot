package ru.muryginds.infoStorage.bot;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

  @Getter
  private String botUsername;
  @Getter
  private String botToken;

  public Bot (@Value("${bot.name}") String userName,
      @Value("${bot.token}") String botToken) {
    this.botUsername = userName;
    this.botToken = botToken;
  }

  @Override
  public void onUpdateReceived(Update update) {
    try {
      if (update.hasMessage()) {
        Message inMessage = update.getMessage();
        boolean isForwarded = inMessage.getForwardDate() != null;
        SendMessage outMessage = new SendMessage();
        outMessage.setChatId(inMessage.getChatId().toString());
        if (inMessage.hasText() && isForwarded) {
          outMessage.setText(inMessage.getText());
        } else {
          outMessage.setReplyToMessageId(update.getMessage().getMessageId());
          outMessage.setText("msg_id: " + update.getMessage().getMessageId()
              + ". user_id: " + update.getMessage().getFrom().getId());
        }
        execute(outMessage);
      }
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
}
