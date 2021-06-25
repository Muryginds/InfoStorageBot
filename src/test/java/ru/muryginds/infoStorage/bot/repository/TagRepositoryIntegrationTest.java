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
import ru.muryginds.infoStorage.bot.models.Tag;
import ru.muryginds.infoStorage.bot.models.User;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class TagRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void whenGetByNameAndUser_thenReturnOptionalTag() {

        String testTagName = "$test_tag";
        User newUser = new User("425425", "testname", BotState.WORKING);
        Tag tag = new Tag(testTagName, newUser);

        entityManager.persist(newUser);
        entityManager.persist(tag);
        entityManager.flush();

        Optional<Tag> found = tagRepository.getByNameAndUser(testTagName, newUser);

        Assertions.assertThat(found.get().getName()).isEqualTo(testTagName);
    }
}