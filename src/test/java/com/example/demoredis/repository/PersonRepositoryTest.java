package com.example.demoredis.repository;

import com.example.demoredis.entity.Address;
import com.example.demoredis.entity.Person;
import com.example.demoredis.entity.Person2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    Person2Repository person2Repository;

    @Test
    void save() {
        for (int i = 0; i < 10; i++) {
            Address address = Address.builder()
                    .city("City" + i)
                    .country("Country" + i)
                    .street("Street" + i)
                    .zip("Zip" + i)
                    .build();
            Person person = Person.builder()
                    .id(String.valueOf(i))
                    .firstname("John" + i)
                    .lastname("Doe" + i)
                    .address(address)
                    .build();
            personRepository.save(person);
        }
        personRepository.findById("1").ifPresent(System.out::println);
        long count = personRepository.count();
        System.out.println("count = " + count);
    }

    @Test
    void save2() {
        for (int i = 0; i < 10; i++) {
            Address address = Address.builder()
                    .city("City" + i)
                    .country("Country" + i)
                    .street("Street" + i)
                    .zip("Zip" + i)
                    .build();
            Person2 person = Person2.builder()
                    .id(String.valueOf(i))
                    .firstname("John" + i)
                    .lastname("Doe" + i)
                    .address(address)
                    .build();
            person2Repository.save(person);
        }
        personRepository.findById("1").ifPresent(System.out::println);
        long count = personRepository.count();
        System.out.println("count = " + count);
    }

    @Test
    void findByIdTest() {
        Person byId = personRepository.findById("1").orElse(new Person());
        System.out.println("byId = " + byId);
        System.out.println("byId = " + byId.getAddress().getCity());
    }

    @Test
    void updateTest() {
        personRepository.findById("1").ifPresent(person -> {
            person.setFirstname("Jane");
            personRepository.save(person);
        });
    }

    @Test
    void deleteTest() {
        personRepository.deleteById("1");
    }
}