package ru.muryginds.infoStorage.bot.keyboards;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.muryginds.infoStorage.bot.service.NoteAdditionControl;
import ru.muryginds.infoStorage.bot.utils.Utils;

@Component("addingTagsKeyboardMessage")
public class AddingTagsKeyboardMessage implements AbstractKeyboardMessage {

  @Autowired
  @Qualifier("noteAdditionControl")
  NoteAdditionControl noteAdditionControl;

  @Override
  public SendMessage sendKeyboardMessage(long chatId, int messageId) {

    SendMessage message = Utils.prepareSendMessage(chatId,
        "Would you like to add this tags?");

    message.setReplyToMessageId(noteAdditionControl.getMessageIdByChatId(chatId));
    message.setReplyMarkup(formKeyboard());

    return message;
  }

  @Override
  public ReplyKeyboard formKeyboard() {

    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
    keyboardButtonsRow.add(createInlineKeyboardButton("Add", "AddingTagsAddToDb"));
    keyboardButtonsRow.add(createInlineKeyboardButton("Edit", "AddingTagsEdit"));
    keyboardButtonsRow.add(createInlineKeyboardButton("Cancel", "AddingTagsCancel"));
    rowList.add(keyboardButtonsRow);
    inlineKeyboardMarkup.setKeyboard(rowList);

    return inlineKeyboardMarkup;
  }
}