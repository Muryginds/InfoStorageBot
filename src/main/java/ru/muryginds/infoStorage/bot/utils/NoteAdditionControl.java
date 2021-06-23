package ru.muryginds.infoStorage.bot.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component("noteAdditionControl")
public class NoteAdditionControl {

  private final Map<Long, Integer> tempNotesList =
      new HashMap<>();


  public void add(Message message) {
    tempNotesList.put(message.getChatId(), message.getMessageId());
  }

  public int getMessageIdByChatId(long id) {
    return tempNotesList.get(id);
  }

  public void remove(long id) {
    tempNotesList.remove(id);
  }
}