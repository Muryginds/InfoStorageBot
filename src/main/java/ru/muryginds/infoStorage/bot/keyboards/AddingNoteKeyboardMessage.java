package ru.muryginds.infoStorage.bot.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.muryginds.infoStorage.bot.utils.Constants;
import ru.muryginds.infoStorage.bot.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Component("addingNoteKeyboardMessage")
public class AddingNoteKeyboardMessage implements AbstractKeyboardMessage {

  @Override
  public SendMessage sendKeyboardMessage(long chatId, int messageId, String text) {

    SendMessage message = Utils.prepareSendMessage(chatId, text);
    message.setReplyToMessageId(messageId);
    message.setReplyMarkup(formKeyboard());

    return message;
  }

  @Override
  public ReplyKeyboard formKeyboard() {

    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
    keyboardButtonsRow.add(createInlineKeyboardButton("Yes",
            Constants.KEYBOARD_ADD_NOTE_BUTTON_YES_COMMAND));
    keyboardButtonsRow.add(createInlineKeyboardButton("No",
            Constants.KEYBOARD_ADD_NOTE_BUTTON_NO_COMMAND));
    rowList.add(keyboardButtonsRow);
    inlineKeyboardMarkup.setKeyboard(rowList);

    return inlineKeyboardMarkup;
  }
}