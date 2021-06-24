package ru.muryginds.infoStorage.bot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Key key = (Key) o;
      return Objects.equals(message, key.message) && Objects.equals(tag, key.tag);
    }

    @Override
    public int hashCode() {
      return Objects.hash(message, tag);
    }
  }
}