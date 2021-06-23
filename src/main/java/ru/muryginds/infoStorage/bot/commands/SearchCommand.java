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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
      } else {
        break;
      }
    }

    if (tagNames.size() > 0) {
      var curUser =
          userRepository.getByChatId(String.valueOf(chat.getId()))
              .orElseGet(() -> userRepository.save(
                  new ru.muryginds.infoStorage.bot.models.User(chat.getId(), user)));
      Set<Tag> tags = new HashSet<>();
      for (String tag: tagNames) {
        tagRepository.getByNameAndUser(tag, curUser).ifPresent(tags::add);
      }
      List<Integer> messagesId =
          chatMessageWithTagRepository.getListMessageIdByTagIn(tags, tags.size());
      for(int number: messagesId) {
        SendMessage message = new SendMessage();
        message.setReplyToMessageId(number);
        message.setChatId(curUser.getChatId());
        message.setText(""+'\u2b08');
        try {
          absSender.execute(message);
        } catch (TelegramApiException e) {
          e.printStackTrace();
        }
      }
    }
  }
}