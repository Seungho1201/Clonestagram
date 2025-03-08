package com.example.clone.Post;

import com.example.clone.Member.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 메인 페이지로 포스트 데이터 전송 서비스
    public List<Post> sendPostList() {

        // 게시글 리스트 페이지 전송
        List<Post> postData2 = postRepository.findAll();

        return postData2;
    }

    // 메인 페이지로 전송되는 유저 데이터 (이름, 아이디)
    public MyUserDetailsService.CustomUser sendUserData(Authentication auth) {
        // 유저 정보 페이지 전송
        MyUserDetailsService.CustomUser user = (MyUserDetailsService.CustomUser) auth.getPrincipal();

        return user;
    }


}
