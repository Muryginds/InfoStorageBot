package ru.muryginds.infoStorage.bot.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.muryginds.infoStorage.bot.enums.BotState;
import ru.muryginds.infoStorage.bot.utils.Utils;

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
  @Column(columnDefinition = "enum", name = "bot_state")
  private BotState botState;

/*  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<ChatMessage> chatMessages;*/

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<Tag> tagList;

  public User(Message message) {
    this.chatId = String.valueOf(message.getChatId());
    this.name = Utils.getUserName(message.getFrom());
    this.botState = BotState.WORKING;
  }
}