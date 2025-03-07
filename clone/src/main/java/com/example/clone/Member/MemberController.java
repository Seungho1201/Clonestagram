package com.example.clone.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
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

    @GetMapping("/mypage")
    String myPage(Authentication auth){

        MyUserDetailsService.CustomUser user = (MyUserDetailsService.CustomUser) auth.getPrincipal();

        System.out.println(user.userId);

        return "mypage.html";
    }
}
