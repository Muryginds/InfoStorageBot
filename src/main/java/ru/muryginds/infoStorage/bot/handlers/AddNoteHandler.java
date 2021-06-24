package ru.muryginds.infoStorage.bot.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.muryginds.infoStorage.bot.enums.BotState;
import ru.muryginds.infoStorage.bot.models.User;
import ru.muryginds.infoStorage.bot.repository.UserRepository;
import ru.muryginds.infoStorage.bot.utils.Constants;
import ru.muryginds.infoStorage.bot.utils.NoteAdditionControl;
import ru.muryginds.infoStorage.bot.utils.TempMessagesControl;
import ru.muryginds.infoStorage.bot.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Component("addNoteHandler")
public class AddNoteHandler implements AbstractHandler {

  private final UserRepository userRepository;
  private final NoteAdditionControl noteAdditionControl;
  private final TempMessagesControl tempMessagesControl;

  @Autowired
  public AddNoteHandler (UserRepository userRepository,
        NoteAdditionControl noteAdditionControl, TempMessagesControl tempMessagesControl) {
    this.userRepository = userRepository;
    this.noteAdditionControl = noteAdditionControl;
    this.tempMessagesControl = tempMessagesControl;
  }

  @Override
  public List<BotApiMethod<?>> getAnswerList(User user, BotApiObject botApiObject) {
    List<BotApiMethod<?>> answer = new ArrayList<>();
    CallbackQuery callbackQuery = (CallbackQuery) botApiObject;
    String data = callbackQuery.getData();

    switch (data) {
      case Constants.KEYBOARD_ADD_NOTE_BUTTON_YES_COMMAND:
        answer.add(Utils.sendAnswerCallbackQuery(Constants.ADDING_MESSAGE_TO_MEMORY,false, callbackQuery));
        answer.add(Utils.prepareSendMessage(callbackQuery.getMessage().getChatId(),
                Constants.ASK_SET_TAGS));
        tempMessagesControl.add(callbackQuery.getMessage());
        noteAdditionControl.add(callbackQuery.getMessage().getReplyToMessage());
        user.setBotState(BotState.ADDING_TAGS);
        userRepository.save(user);
        break;
      case Constants.KEYBOARD_ADD_NOTE_BUTTON_NO_COMMAND:
        answer.add(Utils.sendAnswerCallbackQuery(Constants.ADDING_CANCELLED
            ,false, callbackQuery));
        tempMessagesControl.add(callbackQuery.getMessage());
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
    return List.of(Constants.KEYBOARD_ADD_NOTE_OPERATED_CALLBACK);
  }
}