package ru.muryginds.infoStorage.bot.keyboards;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public interface AbstractKeyboardMessage {

  SendMessage sendKeyboardMessage(long chatId, int messageId);

  ReplyKeyboard formKeyboard();

  default InlineKeyboardButton createInlineKeyboardButton(String text,
      String command) {
    InlineKeyboardButton button = new InlineKeyboardButton();
    button.setText(text);
    button.setCallbackData(command);
    return button;
  }
}
