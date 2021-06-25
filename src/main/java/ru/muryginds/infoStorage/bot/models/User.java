package ru.muryginds.infoStorage.bot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.muryginds.infoStorage.bot.enums.BotState;
import ru.muryginds.infoStorage.bot.utils.Utils;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity {

  @Column(name = "chat_id")
  private String chatId;

  @Column(name = "name")
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "bot_state")
  private BotState botState;

  public User(Message message) {
    this.chatId = String.valueOf(message.getChatId());
    this.name = Utils.getUserName(message.getFrom());
    this.botState = BotState.WORKING;
  }

  public User(Long chatId, org.telegram.telegrambots.meta.api.objects.User user) {
    this.chatId = String.valueOf(chatId);
    this.name = Utils.getUserName(user);
    this.botState = BotState.WORKING;
  }
}