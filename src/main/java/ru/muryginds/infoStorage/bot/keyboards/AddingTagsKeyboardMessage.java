package ru.muryginds.infoStorage.bot.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.muryginds.infoStorage.bot.utils.Constants;
import ru.muryginds.infoStorage.bot.utils.NoteAdditionControl;
import ru.muryginds.infoStorage.bot.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Component("addingTagsKeyboardMessage")
public class AddingTagsKeyboardMessage implements AbstractKeyboardMessage {

  private final NoteAdditionControl noteAdditionControl;

  public AddingTagsKeyboardMessage(NoteAdditionControl noteAdditionControl) {
    this.noteAdditionControl = noteAdditionControl;
  }

  @Override
  public SendMessage sendKeyboardMessage(long chatId, int messageId, String text) {

    SendMessage message = Utils.prepareSendMessage(chatId, text);
    message.setReplyToMessageId(noteAdditionControl.getMessageIdByChatId(chatId));
    message.setReplyMarkup(formKeyboard());

    return message;
  }

  @Override
  public ReplyKeyboard formKeyboard() {

    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
    keyboardButtonsRow.add(createInlineKeyboardButton("Add",
            Constants.KEYBOARD_ADD_TAG_BUTTON_ADD_COMMAND));
    keyboardButtonsRow.add(createInlineKeyboardButton("Edit",
            Constants.KEYBOARD_ADD_TAG_BUTTON_EDIT_COMMAND));
    keyboardButtonsRow.add(createInlineKeyboardButton("Cancel",
            Constants.KEYBOARD_ADD_TAG_BUTTON_CANCEL_COMMAND));
    rowList.add(keyboardButtonsRow);
    inlineKeyboardMarkup.setKeyboard(rowList);

    return inlineKeyboardMarkup;
  }
}