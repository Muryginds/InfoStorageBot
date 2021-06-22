package ru.muryginds.infoStorage.bot.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.muryginds.infoStorage.bot.models.Tag;
import ru.muryginds.infoStorage.bot.models.User;

@Repository
@Transactional(readOnly = true)
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> getByNameAndUser(String name, User user);
    List<Tag> findAllByUser(User user);
}