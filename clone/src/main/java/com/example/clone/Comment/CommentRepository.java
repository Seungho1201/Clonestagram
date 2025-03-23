package com.example.clone.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllById(Long id);

    // 해당 게시글의 댓글 가져오는 쿼리
    @Query(value="select * from comment where post_id = ?1;", nativeQuery = true)
    List<Comment> findByPostId(Long id);


}
