package ru.muryginds.infoStorage.bot.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.muryginds.infoStorage.bot.enums.BotState;
import ru.muryginds.infoStorage.bot.models.User;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenGetByChatId_thenReturnOptionalUser() {

        User newUser = new User("425425", "testname", BotState.WORKING);
        entityManager.persist(newUser);
        entityManager.flush();

        Optional<User> found = userRepository.getByChatId(newUser.getChatId());

        Assertions.assertThat(found.get().getChatId()).isEqualTo(newUser.getChatId());
    }
}