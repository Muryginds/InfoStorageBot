package ru.muryginds.infoStorage.bot;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import ru.muryginds.infoStorage.bot.handlers.UpdateHandler;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
public class BotTest {

    @Autowired
    private UpdateHandler updateHandler;

    @Autowired
    private BotCommand[] myBotCommands;

    @Test
    public void injectedComponentsAreNotNull() {
        Assertions.assertThat(updateHandler).isNotNull();
        Assertions.assertThat(myBotCommands).isNotNull();
    }
}