package ru.muryginds.infoStorage.bot.handlers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.muryginds.infoStorage.bot.enums.BotState;
import ru.muryginds.infoStorage.bot.keyboards.AbstractKeyboardMessage;
import ru.muryginds.infoStorage.bot.models.User;
import ru.muryginds.infoStorage.bot.repository.JpaUserRepository;

@Component("messageHandler")
public class MessageHandler implements AbstractHandler {

  @Autowired
  @Qualifier("addingNoteKeyboardMessage")
  AbstractKeyboardMessage addingNoteKeyboardMessage;

  private final JpaUserRepository userRepository;

  @Autowired
  public MessageHandler (JpaUserRepository jpaUserRepository) {
    this.userRepository = jpaUserRepository;
  }

  @Override
  public List<BotApiMethod<?>> getAnswerList(User user, BotApiObject botApiObject) {
    List<BotApiMethod<?>> answer = new ArrayList<>();
    Message message = ((Message) botApiObject);
    long chatId = message.getChatId();
    answer.add(addingNoteKeyboardMessage.sendKeyboardMessage(chatId, message.getMessageId()));
    user.setBotState(BotState.ADDING_TAGS);
    userRepository.save(user);
    return answer;
  }

  @Override
  public BotState getOperatedBotState() {
    return BotState.WORKING;
  }
}