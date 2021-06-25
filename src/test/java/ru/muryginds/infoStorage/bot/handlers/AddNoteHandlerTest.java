package ru.muryginds.infoStorage.bot.handlers;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.muryginds.infoStorage.bot.repository.UserRepository;
import ru.muryginds.infoStorage.bot.utils.NoteAdditionControl;
import ru.muryginds.infoStorage.bot.utils.TempMessagesControl;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
public class AddNoteHandlerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TempMessagesControl tempMessagesControl;

    @Autowired
    private NoteAdditionControl noteAdditionControl;

    @Test
    public void injectedComponentsAreNotNull() {
        Assertions.assertThat(userRepository).isNotNull();
        Assertions.assertThat(noteAdditionControl).isNotNull();
        Assertions.assertThat(tempMessagesControl).isNotNull();
    }
}