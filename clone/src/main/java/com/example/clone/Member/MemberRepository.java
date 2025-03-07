package com.example.clone.Member;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {

    // 아이디로 구분
    Optional<Member> findByUserId(String userId);
}
