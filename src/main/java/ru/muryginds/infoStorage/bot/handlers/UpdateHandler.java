package ru.muryginds.infoStorage.bot.handlers;

import static ru.muryginds.infoStorage.bot.keyBoard.SendInlineKeyBoard.sendInlineKeyBoardMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.muryginds.infoStorage.bot.commands.NonCommand;
import ru.muryginds.infoStorage.bot.utils.Utils;

@Component("updateHandler")
public class UpdateHandler {

  @Autowired
  @Qualifier("nonCommand")
  private NonCommand nonCommand;

  public SendMessage handleUpdate(Update update) {

    Message msg;
    Long chatId;
    String userName;
    String answer = "";

    if(update.hasMessage()){
      if(update.getMessage().hasText() && !update.getMessage().getText().equals("Hello")){
        msg = update.getMessage();
        userName = Utils.getUserName(msg.getFrom());
        chatId = msg.getChatId();
        answer = nonCommand.nonCommandExecute(chatId, userName, msg.getText());
        return setAnswer(chatId, userName, answer);
      } else if (update.getMessage().getText().equals("Hello")){

        return sendInlineKeyBoardMessage(update.getMessage().getChatId(),
            update.getMessage().getMessageId());

      }
    }else if(update.hasCallbackQuery()) {
      msg = update.getCallbackQuery().getMessage();
      userName = Utils.getUserName(msg.getFrom());
      chatId = msg.getChatId();
      answer = nonCommand.nonCommandExecute(chatId, userName,
          update.getCallbackQuery().getData());
      return setAnswer(chatId, userName, answer);
    }

    //if(){
//          try {
//            execute(sendInlineKeyBoard.sendInlineKeyBoardMessage(update.getMessage().getChatId()));
//          } catch (TelegramApiException e) {
//            e.printStackTrace();
//          }
    //}
    return new SendMessage();
  }

  private static SendMessage setAnswer(Long chatId, String userName, String text) {
    SendMessage answer = new SendMessage();
    answer.setText(text);
    answer.setChatId(chatId.toString());

    return answer;
  }
}
