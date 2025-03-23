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

        // 아이디 이미 존재할시 다시 가입 페이지로
        if(memberRepository.findByUserId(userId).isPresent()){
            return "redirect:/signup";
        }

        var member = new Member();

        member.setUserId(userId);
        member.setUserPassword(passwordEncoder.encode(userPassword));
        member.setUserName(userName);
        member.setUserEmail(userEmail);

        memberRepository.save(member);

        return "redirect:/login";
    }

    // 마이페이지
    @GetMapping("/mypage")
    String myPage(Model model, Authentication auth){

        // 로그인 X일시 로그인 페이지로
        if(auth == null){ return "login.html"; }

        // 유저 정보 얻기
        MyUserDetailsService.CustomUser user = (MyUserDetailsService.CustomUser) auth.getPrincipal();

        List<Post> userPost =  postRepository.findByPostUserId(user.userId);

        //System.out.println(userPost);

        String userName = user.getUsername();
        String userId = user.userId;

        model.addAttribute("userPost", userPost);
        model.addAttribute("userName", userName);
        model.addAttribute("userId", userId);

        return "mypage.html";
    }


}
