package com.example.clone.Post;

import com.example.clone.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 아이디로 구분
    List<Post> findByPostUserId(String userId);

    // full text index로 검색하기
    // match ~ against 쓰면 검색 순위 자동 정렬해줌
    // against(?1) = text 값과 관련있는 데이터 찾음
    // ALTER TABLE post ADD FULLTEXT(post_content); 해서 디비에 미리 인덱스 테이블을 만들어줘야 함
    @Query(value = "select * from post where match(post_content) against(?1);", nativeQuery = true)
    List<Post> searchQuery(String text);
}
