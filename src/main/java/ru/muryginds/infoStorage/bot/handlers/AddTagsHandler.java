package ru.muryginds.infoStorage.bot.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import ru.muryginds.infoStorage.bot.utils.NoteAdditionControl;
import ru.muryginds.infoStorage.bot.utils.TempMessagesControl;
import ru.muryginds.infoStorage.bot.utils.Utils;

@Component("addTagsHandler")
public class AddTagsHandler implements AbstractHandler {

  public static final String ADDING_TAGS_ADD_NOTE_TO_DB = "AddingTagsAddNoteToDb";
  public static final String ADDING_TAGS_EDIT = "AddingTagsEdit";
  public static final String ADDING_TAGS_CANCEL = "AddingTagsCancel";
  public static final String TAG_PATTERN = "(\\$[\\w]+)+";

  @Autowired
  @Qualifier("addingTagsKeyboardMessage")
  private AbstractKeyboardMessage addingTagsKeyboardMessage;

  @Autowired
  @Qualifier("tempMessagesControl")
  TempMessagesControl tempMessagesControl;

  @Autowired
  @Qualifier("noteAdditionControl")
  NoteAdditionControl noteAdditionControl;

  private final UserRepository userRepository;
  private final TagRepository tagRepository;
  private final ChatMessageRepository chatMessageRepository;
  private final ChatMessageWithTagRepository chatMessageWithTagRepository;

  private final Map<Integer, Set<String>> userTags = new HashMap<>();


  @Autowired
  public AddTagsHandler (UserRepository userRepository,
      TagRepository tagRepository, ChatMessageRepository chatMessageRepository,
      ChatMessageWithTagRepository chatMessageWithTagRepository) {
    this.userRepository = userRepository;
    this.tagRepository = tagRepository;
    this.chatMessageRepository = chatMessageRepository;
    this.chatMessageWithTagRepository = chatMessageWithTagRepository;
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
            message.getMessageId(), "Would you like to add this tags? " + tags));
      } else {
        answer.add(Utils.prepareSendMessage(chatId, "No tags found, please send again"));
      }
      tempMessagesControl.add(message);
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
      case ADDING_TAGS_ADD_NOTE_TO_DB:
        answer.add(Utils.sendAnswerCallbackQuery("Adding tags now"
            + " to memory",false, callbackQuery));
        Set<String> newTags = userTags.get(user.getId());
        Set<Tag> tags = new HashSet<>();
        for (String tag: newTags) {
          tags.add(tagRepository.getByNameAndUser(tag, user).orElse(new Tag(tag, user)));
        }
        int noteId = noteAdditionControl.getMessageIdByChatId(callbackQuery.getMessage()
            .getChatId());
        tagRepository.saveAll(tags);
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
            "note added!"));
        noteAdditionControl.remove(callbackQuery.getMessage().getChatId());
        tempMessagesControl.add(callbackQuery.getMessage());
        answer.addAll(
            tempMessagesControl.removeAllByChatId(callbackQuery.getMessage().getChatId()));
        break;
      case ADDING_TAGS_EDIT:
        tempMessagesControl.add(callbackQuery.getMessage());
        answer.add(Utils.sendAnswerCallbackQuery("Edit the tags!"
            ,false, callbackQuery));
        answer.add(Utils.prepareSendMessage(callbackQuery.getMessage().getChatId(),
            "please set tags for this note again"));
        break;
      case ADDING_TAGS_CANCEL:
        answer.add(Utils.sendAnswerCallbackQuery("Adding tags was cancelled"
            ,false, callbackQuery));
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
    return List.of("AddingTags");
  }
}