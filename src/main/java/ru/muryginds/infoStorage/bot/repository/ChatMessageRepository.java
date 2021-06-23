package ru.muryginds.infoStorage.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.muryginds.infoStorage.bot.models.ChatMessage;

@Repository
@Transactional()
public interface ChatMessageRepository
    extends JpaRepository<ChatMessage, Integer> {
}