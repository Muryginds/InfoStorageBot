package ru.muryginds.infoStorage.bot.handlers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("callbackQueryUpdateHandler")
@Order(100)
public class CallbackQueryUpdateHandler implements AbstractTypeUpdateHandler {

  @Override
  public List<BotApiMethod<?>> formAnswerList(Update update) {

    CallbackQuery callbackQuery = update.getCallbackQuery();
    long chatId = callbackQuery.getMessage().getChatId();
    String data = callbackQuery.getData();
    List<BotApiMethod<?>> answer = new ArrayList<>();

    switch (data) {
      case "AddingYes":
        BotApiMethod<?> alertAddingYes = sendAnswerCallbackQuery("Adding this message to memory"
            + " memory",false, update.getCallbackQuery());
        answer.add(alertAddingYes);
        break;
      case "AddingNo":
        BotApiMethod<?> alertAddingNo = sendAnswerCallbackQuery("Addition is cancelled"
                + " memory",false, update.getCallbackQuery());
        BotApiMethod<?> delete = sendDeleteMessage(callbackQuery.getMessage());
        answer.add(alertAddingNo);
        answer.add(delete);
        break;
      default:
        answer.add(setAnswer(chatId, data));
    }
    return answer;
  }

  private AnswerCallbackQuery sendAnswerCallbackQuery(String text,
      boolean alert, CallbackQuery callbackQuery) {

    AnswerCallbackQuery answerCallbackQuery =
        new AnswerCallbackQuery();
    answerCallbackQuery.setCallbackQueryId(
        callbackQuery.getId());
    answerCallbackQuery.setShowAlert(alert);
    answerCallbackQuery.setText(text);

    return answerCallbackQuery;
  }

  private DeleteMessage sendDeleteMessage(Message message) {

    DeleteMessage deleteMessage = new DeleteMessage();
    deleteMessage.setChatId(message.getChatId().toString());
    deleteMessage.setMessageId(message.getMessageId());

    return deleteMessage;
  }
}