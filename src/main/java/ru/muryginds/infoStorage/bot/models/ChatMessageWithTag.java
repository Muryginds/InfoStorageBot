package ru.muryginds.infoStorage.bot.models;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "messages_with_tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageWithTag {

  @EmbeddedId
  private Key id;

  @Embeddable
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Key implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    private ChatMessage message;

    @ManyToOne(cascade = CascadeType.ALL)
    private Tag tag;
  }
}