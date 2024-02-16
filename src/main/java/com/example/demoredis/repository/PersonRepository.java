package com.example.demoredis.repository;

import com.example.demoredis.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String> {
    
}

