package ru.muryginds.infoStorage.bot.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component("noteAdditionControl")
public class NoteAdditionControl {

  private final Map<Long, Integer> incompleteNotesList =
      new HashMap<>();

  public boolean checkAwaitingTags(long id) {

    return incompleteNotesList.containsKey(id);
  }

  public void add(Message message) {

    incompleteNotesList.put(message.getChatId(), message.getMessageId());

  }

  public int getMessageIdByChatId(long id) {
    return incompleteNotesList.get(id);
  }

  public void remove(long id) {
    incompleteNotesList.remove(id);
  }
}