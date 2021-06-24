package ru.muryginds.infoStorage.bot.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.muryginds.infoStorage.bot.enums.BotState;
import ru.muryginds.infoStorage.bot.keyboards.AbstractKeyboardMessage;
import ru.muryginds.infoStorage.bot.models.ChatMessage;
import ru.muryginds.infoStorage.bot.models.ChatMessageWithTag;
import ru.muryginds.infoStorage.bot.models.ChatMessageWithTag.Key;
import ru.muryginds.infoStorage.bot.models.Tag;
import ru.muryginds.infoStorage.bot.models.User;
import ru.muryginds.infoStorage.bot.repository.ChatMessageRepository;
import ru.muryginds.infoStorage.bot.repository.ChatMessageWithTagRepository;
import ru.muryginds.infoStorage.bot.repository.TagRepository;
import ru.muryginds.infoStorage.bot.repository.UserRepository;
import ru.muryginds.infoStorage.bot.utils.Constants;
import ru.muryginds.infoStorage.bot.utils.NoteAdditionControl;
import ru.muryginds.infoStorage.bot.utils.TempMessagesControl;
import ru.muryginds.infoStorage.bot.utils.Utils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component("addTagsHandler")
public class AddTagsHandler implements AbstractHandler {

  public static final String TAG_PATTERN = "(\\$[\\w]+)+";
  public static final String ASK_EDIT = "Edit the tags!";

  private final AbstractKeyboardMessage addingTagsKeyboardMessage;
  private final TempMessagesControl tempMessagesControl;
  private final NoteAdditionControl noteAdditionControl;
  private final UserRepository userRepository;
  private final TagRepository tagRepository;
  private final ChatMessageRepository chatMessageRepository;
  private final ChatMessageWithTagRepository chatMessageWithTagRepository;

  private final Map<Integer, Set<String>> userTags = new HashMap<>();


  @Autowired
  public AddTagsHandler (UserRepository userRepository,
      TagRepository tagRepository, ChatMessageRepository chatMessageRepository,
      ChatMessageWithTagRepository chatMessageWithTagRepository,
      AbstractKeyboardMessage addingTagsKeyboardMessage, TempMessagesControl tempMessagesControl,
      NoteAdditionControl noteAdditionControl) {
    this.userRepository = userRepository;
    this.tagRepository = tagRepository;
    this.chatMessageRepository = chatMessageRepository;
    this.chatMessageWithTagRepository = chatMessageWithTagRepository;
    this.addingTagsKeyboardMessage = addingTagsKeyboardMessage;
    this.tempMessagesControl = tempMessagesControl;
    this.noteAdditionControl = noteAdditionControl;
  }

  @Override
  public List<BotApiMethod<?>> getAnswerList(User user, BotApiObject botApiObject) {
    List<BotApiMethod<?>> answer = new ArrayList<>();

    if (botApiObject instanceof Message) {
      Message message = ((Message) botApiObject);
      userTags.put(user.getId(), getTagsFromText(message.getText()));
      long chatId = message.getChatId();
      Set<String> tags = userTags.get(user.getId());
      if (tags.size() > 0) {
        answer.add(addingTagsKeyboardMessage.sendKeyboardMessage(chatId,
            message.getMessageId(), Constants.ASK_GET_TAGS + tags));
      } else {
        answer.add(Utils.prepareSendMessage(chatId, Constants.TAGS_NOT_FOUND));
        tempMessagesControl.add(message);
      }

    } else if (botApiObject instanceof CallbackQuery) {
      CallbackQuery callbackQuery = (CallbackQuery) botApiObject;
      answer.addAll(handleCallBackQuery(user, callbackQuery));
    }
    return answer;
  }

  private List<BotApiMethod<?>> handleCallBackQuery(User user, CallbackQuery callbackQuery) {
    List<BotApiMethod<?>> answer = new ArrayList<>();
    String data = callbackQuery.getData();
    switch (data) {
      case Constants.KEYBOARD_ADD_TAG_BUTTON_ADD_COMMAND:
        answer.add(Utils.sendAnswerCallbackQuery(Constants.ADDING_TAGS_TO_MEMORY,false, callbackQuery));
        Set<String> newTags = userTags.get(user.getId());
        Set<Tag> tags = new HashSet<>();
        for (String tag: newTags) {
          tags.add(tagRepository.getByNameAndUser(tag, user).orElse(new Tag(tag, user)));
        }
        tagRepository.saveAll(tags);
        int noteId = noteAdditionControl.getMessageIdByChatId(callbackQuery.getMessage()
            .getChatId());
        ChatMessage chatMessage = new ChatMessage(noteId, user);
        chatMessageRepository.save(chatMessage);
        Set<ChatMessageWithTag> chatMessageWithTags = new HashSet<>();
        for (Tag tag: tags) {
          chatMessageWithTags.add(new ChatMessageWithTag(new Key(chatMessage, tag)));
        }
        chatMessageWithTagRepository.saveAll(chatMessageWithTags);
        user.setBotState(BotState.WORKING);
        userRepository.save(user);
        answer.add(Utils.prepareSendMessage(callbackQuery.getMessage().getChatId(),
            Constants.ADDING_SUCCESSFUL));
        noteAdditionControl.remove(callbackQuery.getMessage().getChatId());
        tempMessagesControl.add(callbackQuery.getMessage());
        answer.addAll(
            tempMessagesControl.removeAllByChatId(callbackQuery.getMessage().getChatId()));
        break;
      case Constants.KEYBOARD_ADD_TAG_BUTTON_EDIT_COMMAND:
        tempMessagesControl.add(callbackQuery.getMessage());
        answer.add(Utils.sendAnswerCallbackQuery(ASK_EDIT,false, callbackQuery));
        answer.add(Utils.prepareSendMessage(callbackQuery.getMessage().getChatId(),
            Constants.ASK_SET_TAGS));
        break;
      case Constants.KEYBOARD_ADD_TAG_BUTTON_CANCEL_COMMAND:
        answer.add(Utils.sendAnswerCallbackQuery(Constants.ADDING_CANCELLED,false, callbackQuery));
        userTags.remove(user.getId());
        user.setBotState(BotState.WORKING);
        userRepository.save(user);
        noteAdditionControl.remove(callbackQuery.getMessage().getChatId());
        tempMessagesControl.add(callbackQuery.getMessage());
        answer.addAll(
            tempMessagesControl.removeAllByChatId(callbackQuery.getMessage().getChatId()));
        break;
    }
    return answer;
  }

  private Set<String> getTagsFromText(String text) {
    return Pattern.compile(TAG_PATTERN).matcher(text).results()
        .map(m -> m.group(1))
        .collect(Collectors.toSet());
  }

  @Override
  public BotState getOperatedBotState() {
    return BotState.ADDING_TAGS;
  }

  @Override
  public List<String> getOperatedCallBackQuery() {
    return List.of(Constants.KEYBOARD_ADD_TAG_OPERATED_CALLBACK);
  }
}