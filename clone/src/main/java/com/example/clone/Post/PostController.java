package com.example.clone.Post;

import com.example.clone.Member.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    // 메인 게시글 API
    @GetMapping("/main")
    String pistList(Model model) {
        List<Post> postData2 = postRepository.findAll();
        model.addAttribute("postData2", postData2);

        return "main.html";
    }

    // 현재 테스트 용으로 쓰고있는 메인 페이지
    @GetMapping("/test")
    String testList(Model model, Authentication auth) {

        // 게시글 리스트 페이지 전송
        List<Post> postData2 = postRepository.findAll();
        model.addAttribute("postData2", postData2);


        if(auth == null){

            model.addAttribute("userName", "오프라인");
            model.addAttribute("userId", "오프라인");

            return "test.html";
        }

        // 유저 정보 페이지 전송
        MyUserDetailsService.CustomUser user = (MyUserDetailsService.CustomUser) auth.getPrincipal();

        String userId = user.userId;
        String userName = user.getUsername();

        model.addAttribute("userName", userName);
        model.addAttribute("userId", userId);

        return "test.html";
    }

    @GetMapping("/post")
    String goPost(Authentication auth) {

        System.out.println(auth);

        return "post.html";
    }

}
