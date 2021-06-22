package ru.muryginds.infoStorage.bot.handlers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.muryginds.infoStorage.bot.enums.BotState;
import ru.muryginds.infoStorage.bot.keyboards.AbstractKeyboardMessage;
import ru.muryginds.infoStorage.bot.models.User;
import ru.muryginds.infoStorage.bot.repository.JpaUserRepository;
import ru.muryginds.infoStorage.bot.utils.Utils;

@Component("addTagsHandler")
public class AddTagsHandler implements AbstractHandler {

  public static final String ADDING_TAGS_ADD_TO_DB = "AddingTagsAddToDb";
  public static final String ADDING_TAGS_EDIT = "AddingTagsEdit";
  public static final String ADDING_TAGS_CANCEL = "AddingTagsCancel";

  @Autowired
  @Qualifier("addingTagsKeyboardMessage")
  private AbstractKeyboardMessage addingTagsKeyboardMessage;

  private final JpaUserRepository userRepository;

  @Autowired
  public AddTagsHandler (JpaUserRepository jpaUserRepository) {
    this.userRepository = jpaUserRepository;
  }

  @Override
  public List<BotApiMethod<?>> getAnswerList(User user, BotApiObject botApiObject) {
    List<BotApiMethod<?>> answer = new ArrayList<>();

    if (botApiObject instanceof Message) {
      Message message = ((Message) botApiObject);
      long chatId = message.getChatId();
      answer.add(addingTagsKeyboardMessage.sendKeyboardMessage(chatId, message.getMessageId()));
    } else if (botApiObject instanceof CallbackQuery) {
      CallbackQuery callbackQuery = (CallbackQuery) botApiObject;
      String data = callbackQuery.getData();
      switch (data) {
        case ADDING_TAGS_ADD_TO_DB:
          answer.add(Utils.sendAnswerCallbackQuery("Adding tags"
              + " to memory",false, callbackQuery));
          answer.add(Utils.prepareSendMessage(callbackQuery.getMessage().getChatId(),
              "thees tags added!"));
          user.setBotState(BotState.WORKING);
          userRepository.save(user);
          break;
        case ADDING_TAGS_EDIT:
          answer.add(Utils.sendAnswerCallbackQuery("Edit the tags!"
              ,false, callbackQuery));
          break;
        case ADDING_TAGS_CANCEL:
          answer.add(Utils.sendAnswerCallbackQuery("Adding tags cancelled"
              ,false, callbackQuery));
          user.setBotState(BotState.WORKING);
          userRepository.save(user);
          break;
      }
    }
    return answer;
  }

  @Override
  public BotState getOperatedBotState() {
    return BotState.ADDING_TAGS;
  }

  @Override
  public List<String> getOperatedCallBackQuery() {
    return List.of("AddingTags");
  }
}