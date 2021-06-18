package ru.muryginds.infoStorage.bot.keyBoard;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class SendInlineKeyBoard {

  public static SendMessage sendInlineKeyBoardMessage(long chatId, int messageId) {
    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
    InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
    InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
    inlineKeyboardButton1.setText("Тык");
    inlineKeyboardButton1.setCallbackData("Button \"Тык\" has been pressed");
    inlineKeyboardButton2.setText("Тык2");
    inlineKeyboardButton2.setCallbackData("Button \"Тык2\" has been pressed");
    inlineKeyboardButton3.setText("Fi4a");
    inlineKeyboardButton3.setCallbackData("CallFi4a");
    List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
    List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
    keyboardButtonsRow1.add(inlineKeyboardButton1);
    keyboardButtonsRow1.add(inlineKeyboardButton3);
    keyboardButtonsRow2.add(inlineKeyboardButton2);
    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
    rowList.add(keyboardButtonsRow1);
    rowList.add(keyboardButtonsRow2);
    inlineKeyboardMarkup.setKeyboard(rowList);
    SendMessage message = new SendMessage();
    message.setChatId(String.valueOf(chatId));
    message.setText("Пример");
    message.setReplyToMessageId(messageId);
    message.setReplyMarkup(inlineKeyboardMarkup);
    return message;
  }
}
