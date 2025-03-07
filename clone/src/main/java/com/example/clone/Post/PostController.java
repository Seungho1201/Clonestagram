package com.example.clone.Post;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/test")
    String testList(Model model) {
        List<Post> postData2 = postRepository.findAll();
        model.addAttribute("postData2", postData2);

        return "test.html";
    }

    @GetMapping("/post")
    String goPost() {
        

        return "post.html";
    }




}
