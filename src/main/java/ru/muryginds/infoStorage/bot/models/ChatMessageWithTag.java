package ru.muryginds.infoStorage.bot.models;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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

  @ManyToOne(cascade = CascadeType.ALL)
  @MapsId("messageId")
  private ChatMessage chatMessage;

  @ManyToOne(cascade = CascadeType.ALL)
  @MapsId("tagsId")
  private Tag tag;

  @Embeddable
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Key implements Serializable {

    @Column(name = "message_id")
    private int messageId;

    @Column(name = "tag_id")
    private int tagId;
  }
}