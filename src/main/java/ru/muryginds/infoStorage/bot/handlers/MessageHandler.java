package ru.muryginds.infoStorage.bot.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.muryginds.infoStorage.bot.enums.BotState;
import ru.muryginds.infoStorage.bot.keyboards.AbstractKeyboardMessage;
import ru.muryginds.infoStorage.bot.models.User;
import ru.muryginds.infoStorage.bot.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component("messageHandler")
public class MessageHandler implements AbstractHandler {

  public static final String MESSAGE_TEXT = "Would you like to store this message?";

  private final AbstractKeyboardMessage addingNoteKeyboardMessage;
  private final UserRepository userRepository;


  @Autowired
  public MessageHandler (UserRepository userRepository, AbstractKeyboardMessage addingNoteKeyboardMessage) {
    this.userRepository = userRepository;
    this.addingNoteKeyboardMessage = addingNoteKeyboardMessage;
  }

  @Override
  public List<BotApiMethod<?>> getAnswerList(User user, BotApiObject botApiObject) {
    List<BotApiMethod<?>> answer = new ArrayList<>();
    Message message = ((Message) botApiObject);
    long chatId = message.getChatId();
    answer.add(addingNoteKeyboardMessage.sendKeyboardMessage(chatId,
        message.getMessageId(), MESSAGE_TEXT));
    user.setBotState(BotState.ADDING_TAGS);
    userRepository.save(user);

    return answer;
  }

  @Override
  public BotState getOperatedBotState() {
    return BotState.WORKING;
  }
}