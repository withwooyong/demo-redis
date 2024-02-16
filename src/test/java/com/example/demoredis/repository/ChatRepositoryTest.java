package com.example.demoredis.repository;

import com.example.demoredis.entity.Chat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ChatRepositoryTest {

    @Autowired
    ChatRepository chatRepository;

    @Test
    void save() {
        List<Chat.Msg> msgs = List.of(
                new Chat.Msg("system", "system message"),
                new Chat.Msg("user", "user message"),
                new Chat.Msg("assistant", "assistant message")
        );

        for (int i = 0; i < 10; i++) {
            Chat chat = Chat.builder()
                    .id(String.valueOf(i))
                    .msgs(msgs)
                    .build();
            chatRepository.save(chat);
        }
    }

    @Test
    void delete() {
        chatRepository.deleteAll();
    }
}