package ru.muryginds.infoStorage.bot.handlers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.muryginds.infoStorage.bot.enums.BotState;
import ru.muryginds.infoStorage.bot.models.User;
import ru.muryginds.infoStorage.bot.repository.JpaUserRepository;
import ru.muryginds.infoStorage.bot.service.NoteAdditionControl;
import ru.muryginds.infoStorage.bot.service.TrashMessagesControl;
import ru.muryginds.infoStorage.bot.utils.Utils;

@Component("addNoteHandler")
public class AddNoteHandler implements AbstractHandler {

  public static final String ADDING_YES = "AddingNoteYes";
  public static final String ADDING_NO = "AddingNoteNo";

  private final JpaUserRepository userRepository;

  @Autowired
  public AddNoteHandler (JpaUserRepository jpaUserRepository) {
    this.userRepository = jpaUserRepository;
  }
  @Autowired
  @Qualifier("noteAdditionControl")
  NoteAdditionControl noteAdditionControl;

  @Autowired
  @Qualifier("trashMessagesControl")
  TrashMessagesControl trashMessagesControl;

  @Override
  public List<BotApiMethod<?>> getAnswerList(User user, BotApiObject botApiObject) {
    List<BotApiMethod<?>> answer = new ArrayList<>();
    CallbackQuery callbackQuery = (CallbackQuery) botApiObject;
    String data = callbackQuery.getData();

    switch (data) {
      case ADDING_YES:
        answer.add(Utils.sendAnswerCallbackQuery("Adding this message"
            + " to memory",false, callbackQuery));
        answer.add(Utils.prepareSendMessage(callbackQuery.getMessage().getChatId(),
            "please set tags for this note"));
        trashMessagesControl.add(callbackQuery.getMessage());
        noteAdditionControl.add(callbackQuery.getMessage().getReplyToMessage());
        user.setBotState(BotState.ADDING_TAGS);
        userRepository.save(user);
        break;
      case ADDING_NO:
        answer.add(Utils.sendAnswerCallbackQuery("Addition is cancelled"
            ,false, callbackQuery));
        trashMessagesControl.add(callbackQuery.getMessage());
        user.setBotState(BotState.WORKING);
        userRepository.save(user);
        break;
    }
    return answer;
  }

  @Override
  public BotState getOperatedBotState() {
    return BotState.ADDING_NOTE;
  }

  @Override
  public List<String> getOperatedCallBackQuery() {
    return List.of("AddingNote");
  }


}