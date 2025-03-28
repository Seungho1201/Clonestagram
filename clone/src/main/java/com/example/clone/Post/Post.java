package com.example.clone.Post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Getter
@Setter
@ToString
@Table(name = "post")
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;            // 게시글 ID
    private Long postRecommend= 0L;     // 게시글 추천 수 기본값 0
    @NonNull
    private String postUserId;      // 게시글 작성 유저
    @NonNull
    private String postContent;     // 게시글 내용
    private String postImg;         // 게시글 이미지

    @CreationTimestamp
    private String postDate;        // 게시글 게시 날짜
}
