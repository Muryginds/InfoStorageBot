package ru.muryginds.infoStorage.bot.commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.muryginds.infoStorage.bot.models.Tag;
import ru.muryginds.infoStorage.bot.repository.ChatMessageWithTagRepository;
import ru.muryginds.infoStorage.bot.repository.TagRepository;
import ru.muryginds.infoStorage.bot.repository.UserRepository;
import ru.muryginds.infoStorage.bot.utils.Constants;
import ru.muryginds.infoStorage.bot.utils.Utils;

import java.util.*;


@Component("searchCommand")
@Order(100)
public class SearchCommand extends ServiceCommand {

  private final UserRepository userRepository;
  private final TagRepository tagRepository;
  private final ChatMessageWithTagRepository chatMessageWithTagRepository;

  public SearchCommand(@Value("search") String identifier,
      @Value("Search") String description, UserRepository userRepository,
      TagRepository tagRepository,
      ChatMessageWithTagRepository chatMessageWithTagRepository) {
    super(identifier, description);
    this.userRepository = userRepository;
    this.tagRepository = tagRepository;
    this.chatMessageWithTagRepository = chatMessageWithTagRepository;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
    List<String> tagNames = new ArrayList<>();

    for (String string : strings) {
      if (string.startsWith("$")) {
        tagNames.add(string);
      }
    }

    boolean messagesFound = false;

    var curUser =
            userRepository.getByChatId(String.valueOf(chat.getId()))
                    .orElseGet(() -> userRepository.save(
                            new ru.muryginds.infoStorage.bot.models.User(chat.getId(), user)));

    List<SendMessage> messages = new ArrayList<>();
    if (tagNames.size() > 0) {

      Set<Tag> tags = new HashSet<>();
      for (String tag: tagNames) {
        tagRepository.getByNameAndUser(tag, curUser).ifPresent(tags::add);
      }

      if (tags.size() == tagNames.size()) {
        List<Integer> messagesId =
          chatMessageWithTagRepository.getListMessageIdByTagIn(tags, tags.size());
        if (messagesId.size() > 0) {
          for(int number: messagesId) {
            SendMessage message = new SendMessage();
            message.setReplyToMessageId(number);
            message.setChatId(curUser.getChatId());
            message.setText(""+'\u2b08');
            messages.add(message);
          }
          messagesFound = true;
          StringBuilder info = new StringBuilder();
          info.append(curUser.getName()).append(" (").append(curUser.getChatId())
                .append(") made successful search with tags: ").append(tagNames)
                .append(" and received ").append(messagesId.size()).append(" message(s)");
          logger.info(info.toString());
        }
      }
    }

    if (!messagesFound) {
      messages.add(Utils.prepareSendMessage(chat.getId(), Constants.BOT_SEARCH_NOT_FOUND + tagNames));
      StringBuilder info = new StringBuilder();
      info.append(curUser.getName()).append(" (").append(curUser.getChatId())
            .append(") made bad search with found tags: ").append(tagNames)
            .append(", requested tags: ").append(Arrays.toString(strings));
      logger.warn(info.toString());
    }

    try {
      for (SendMessage mes : messages) {
        absSender.execute(mes);
      }
    } catch (TelegramApiException e) {
      StringBuilder info = new StringBuilder();
      info.append("Command: ").append(getCommandIdentifier()).append(" User: ").append(curUser.getName());
      logger.error(info.toString(), e);
    }
  }
}