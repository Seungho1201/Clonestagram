package com.example.clone.Post;

import com.example.clone.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 아이디로 구분
    List<Post> findByPostUserId(String userId);
}
