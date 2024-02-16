package com.example.demoredis.repository;

import com.example.demoredis.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRedisRepository memberRedisRepository;

    @Test
    void save() {
        //given
        Member member = new Member("member", 23);
        memberRedisRepository.save(member);
        //when
        Member result = memberRedisRepository.findById(member.getId()).orElseThrow();
        //then
        Assertions.assertThat(result.getName()).isEqualTo(member.getName());
    }

    @Test
    void delete() {
        //given
        Member member = new Member("member", 23);
        memberRedisRepository.save(member);
        //when
        memberRedisRepository.delete(member);
        Member result = memberRedisRepository.findById(member.getId()).orElse(null);
        //then
        Assertions.assertThat(result).isNull();
    }

    @Test
    void deleteAllTest() {
        memberRedisRepository.deleteAll();
    }

    @Test
    void findAllTest() {
        //given
        Member member1 = new Member("member1", 23);
        Member member2 = new Member("member2", 24);
        memberRedisRepository.save(member1);
        memberRedisRepository.save(member2);
        //when
        List<Member> result = (List<Member>) memberRedisRepository.findAll();
        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findAllByIdTest() {
        Iterable<Member> allById = memberRedisRepository.findAllById(List.of("40bc4ddf-e807-4175-8312-41b45b126fc0", "4396bafa-1ed1-4e57-83d1-8d1bdf56755f"));
        allById.forEach(System.out::println);
    }


}