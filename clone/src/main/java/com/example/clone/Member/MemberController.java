package com.example.clone.Member;

import com.example.clone.Post.Post;
import com.example.clone.Post.PostRepository;
import com.example.clone.Post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;
    private final PostRepository postRepository;

    // 로그인 페이지
    @GetMapping("/login")
    String loginPage(){
        return "login.html";
    }

    //회원가입 페이지
    @GetMapping("/signup")
    String signupPage(){
        return "signup.html";
    }

    // 회원가입 기능
    @PostMapping("/signupUser")
    String signupUser(
            @RequestParam String userEmail,
            @RequestParam String userName,
            @RequestParam String userId,
            @RequestParam String userPassword){

        // Service 레이어
        return memberService.addMember(userEmail, userName, userId, userPassword);
    }

    // 마이페이지
    @GetMapping("/mypage")
    String myPage(Model model, Authentication auth){

        // Service 레이어
        return memberService.getMyPage(model, auth);
    }


}
