package ru.muryginds.infoStorage.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.muryginds.infoStorage.bot.models.ChatMessageWithTag;
import ru.muryginds.infoStorage.bot.models.ChatMessageWithTag.Key;

@Repository
@Transactional()
public interface ChatMessageWithTagRepository
    extends JpaRepository<ChatMessageWithTag, Key> {
}