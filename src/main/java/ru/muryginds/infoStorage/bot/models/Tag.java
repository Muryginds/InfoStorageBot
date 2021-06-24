package ru.muryginds.infoStorage.bot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends AbstractEntity {

  @Column(name = "tag_name")
  private String name;

  @ManyToOne(cascade = CascadeType.MERGE)
  private User user;

  @Override
  public String toString() {
    return "Tag{" + name + '}';
  }
}