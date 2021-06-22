package ru.muryginds.infoStorage.bot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component("tempMessagesControl")
public class TempMessagesControl {

  private final Map<Long, List<Integer>> messagesToBeDeleted =
      new HashMap<>();

  public void add(Message message) {
    long chatId = message.getChatId();
    List<Integer> messages = messagesToBeDeleted.get(chatId);
    if (messages == null) {
      messages = new ArrayList<>();
    }
    messages.add(message.getMessageId());

    messagesToBeDeleted.put(chatId, messages);

  }

  public void removeAllByChatId(long id) {
    messagesToBeDeleted.remove(id);
  }
}