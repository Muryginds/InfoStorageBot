package ru.muryginds.infoStorage.bot.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage extends AbstractEntity {

  @Column(name = "message_id")
  private String messageId;

  @ManyToOne(cascade = CascadeType.ALL)
  private User user;

/*  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "messages_with_tags",
      joinColumns = {@JoinColumn(name = "message_id")},
      inverseJoinColumns = {@JoinColumn(name = "tag_id")})
  private List<Tag> tagList;*/
}