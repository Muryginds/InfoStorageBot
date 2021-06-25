package ru.muryginds.infoStorage.bot.handlers;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.muryginds.infoStorage.bot.keyboards.AbstractKeyboardMessage;
import ru.muryginds.infoStorage.bot.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
public class MessageHandlerTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AbstractKeyboardMessage addingNoteKeyboardMessage;

    @Test
    public void injectedComponentsAreNotNull() {
        Assertions.assertThat(userRepository).isNotNull();
        Assertions.assertThat(addingNoteKeyboardMessage).isNotNull();
    }
}