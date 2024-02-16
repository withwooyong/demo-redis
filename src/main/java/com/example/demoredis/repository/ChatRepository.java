package com.example.demoredis.repository;

import com.example.demoredis.entity.Chat;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, String> {

}
