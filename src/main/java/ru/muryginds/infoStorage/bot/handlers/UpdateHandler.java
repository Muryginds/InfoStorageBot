package ru.muryginds.infoStorage.bot.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.muryginds.infoStorage.bot.enums.BotState;
import ru.muryginds.infoStorage.bot.models.User;
import ru.muryginds.infoStorage.bot.repository.TagRepository;
import ru.muryginds.infoStorage.bot.repository.UserRepository;
import ru.muryginds.infoStorage.bot.utils.Utils;

@Component("updateHandler")
@Order(150)
public class UpdateHandler {

  private final UserRepository userRepository;
  private final TagRepository tagRepository;
  private final List<AbstractHandler> handlers;

  @Autowired
  public UpdateHandler(List<AbstractHandler> handlers,
      UserRepository userRepository,
      TagRepository tagRepository) {
    this.handlers = handlers;
    this.userRepository = userRepository;
    this.tagRepository = tagRepository;
  }

  public List<BotApiMethod<?>> handleUpdate(Update update) {

    List<BotApiMethod<?>> result = new ArrayList<>();
    Optional <AbstractHandler> handler;
    User user;

    if(update.hasCallbackQuery()) {
      CallbackQuery callbackQuery = update.getCallbackQuery();
      handler = getHandlerByCallBackQuery(callbackQuery.getData());
      user = userRepository.getByChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()))
          .orElseGet(() -> userRepository.save(new User(update.getMessage())));
      if (handler.isPresent()) {
        result = handler.get().getAnswerList(user, callbackQuery);
      }
    } else if(update.hasMessage()) {
      if (update.getMessage().hasText()) {
        user = userRepository.getByChatId(String.valueOf(update.getMessage().getChatId()))
            .orElseGet(() -> userRepository.save(new User(update.getMessage())));
        //List<Tag> tags = jpaTagRepository.findAllByUser(user);
        handler = getHandlerByState(user.getBotState());
        if (handler.isPresent()) {
          result = handler.get().getAnswerList(user, update.getMessage());
        }
      }
    } else {
      result.add(Utils.prepareSendMessage(update.getMessage().getChatId(), update.getMessage().getText()));
    }

    return result;
  }

  private Optional<AbstractHandler> getHandlerByState(BotState botState) {
    return handlers.stream()
        .filter(h -> h.getOperatedBotState() != null)
        .filter(h -> h.getOperatedBotState().equals(botState))
        .findAny();
  }

  private Optional<AbstractHandler> getHandlerByCallBackQuery(String query) {
    return handlers.stream()
        .filter(h -> h.getOperatedCallBackQuery().stream()
                .anyMatch(query::startsWith))
        .findAny();
  }
}