package com.example.clone.Post;

import com.example.clone.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public String getPostList(Model model, Authentication auth) {

        // 게시글 리스트 페이지 전송
        List<Post> postData2 = postRepository.findAll();

        // 로그인 상태 아니여도 접속 가능
        model.addAttribute("postData2", postData2);

        // 로그아웃 상태일시
        if(auth == null){
            model.addAttribute("userName", "오프라인");
            model.addAttribute("userId", "오프라인");

            return "test.html";
        }

        // 로그인 상태일 시 메인 페이지로 전송할 유저 데이터
        MyUserDetailsService.CustomUser user =  (MyUserDetailsService.CustomUser) auth.getPrincipal();

        model.addAttribute("userName", user.getUsername());
        model.addAttribute("userId", user.userId);

        return "test.html";
    }




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

    // 게시글 업로드 서비스
    public int postFuncService(User user, String postData){
        MyUserDetailsService.CustomUser userData = (MyUserDetailsService.CustomUser) user;

        // 본문 데이터 있어야만 저장
        if(postData.isEmpty()){
            System.out.println("없음 ㅅㄱ");
            return 404;
        }

        Post post = new Post();

        post.setPostUserId(userData.userId);
        post.setPostContent(postData);
        post.setPostImg("아직 이미지 미구현");

        // 데이터 저장
        postRepository.save(post);

        return 200;
    }





}
