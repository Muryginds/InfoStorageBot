package ru.muryginds.infoStorage.bot.handlers;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.muryginds.infoStorage.bot.keyboards.AbstractKeyboardMessage;
import ru.muryginds.infoStorage.bot.repository.ChatMessageRepository;
import ru.muryginds.infoStorage.bot.repository.ChatMessageWithTagRepository;
import ru.muryginds.infoStorage.bot.repository.TagRepository;
import ru.muryginds.infoStorage.bot.repository.UserRepository;
import ru.muryginds.infoStorage.bot.utils.TempMessagesControl;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
public class AddTagsHandlerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TempMessagesControl tempMessagesControl;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatMessageWithTagRepository chatMessageWithTagRepository;


    @Autowired
    private AbstractKeyboardMessage addingTagsKeyboardMessage;

    @Test
    public void injectedComponentsAreNotNull() {
        Assertions.assertThat(userRepository).isNotNull();
        Assertions.assertThat(addingTagsKeyboardMessage).isNotNull();
        Assertions.assertThat(tempMessagesControl).isNotNull();
        Assertions.assertThat(tagRepository).isNotNull();
        Assertions.assertThat(chatMessageRepository).isNotNull();
        Assertions.assertThat(chatMessageWithTagRepository).isNotNull();
    }
}