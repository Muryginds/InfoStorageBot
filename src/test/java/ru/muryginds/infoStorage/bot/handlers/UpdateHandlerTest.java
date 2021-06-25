package ru.muryginds.infoStorage.bot.handlers;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.muryginds.infoStorage.bot.repository.UserRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
public class UpdateHandlerTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private List<AbstractHandler> handlers;

    @Test
    public void injectedComponentsAreNotNull() {
        Assertions.assertThat(userRepository).isNotNull();
        Assertions.assertThat(handlers).isNotNull();
    }
}