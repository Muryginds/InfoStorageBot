package ru.muryginds.infoStorage.bot.repository;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.muryginds.infoStorage.bot.models.User;

@Repository
@Transactional()
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> getByChatId(String chatId);
}