package ru.muryginds.infoStorage.bot.keyboards;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.muryginds.infoStorage.bot.utils.Utils;

@Component("addingNoteKeyboardMessage")
public class AddingNoteKeyboardMessage implements AbstractKeyboardMessage {

  @Override
  public SendMessage sendKeyboardMessage(long chatId, int messageId) {

    SendMessage message = Utils.prepareSendMessage(chatId,
        "Would you like to store this message?");

    message.setReplyToMessageId(messageId);
    message.setReplyMarkup(formKeyboard());

    return message;
  }

  @Override
  public ReplyKeyboard formKeyboard() {

    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
    keyboardButtonsRow.add(createInlineKeyboardButton("Yes", "AddingNoteYes"));
    keyboardButtonsRow.add(createInlineKeyboardButton("No", "AddingNoteNo"));
    rowList.add(keyboardButtonsRow);
    inlineKeyboardMarkup.setKeyboard(rowList);

    return inlineKeyboardMarkup;
  }
}