package com.example.clone.Member;

import com.example.clone.Post.Post;
import com.example.clone.Post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;

    // 멤버 추가 서비스
    public String addMember(String userEmail,
                            String userName,
                            String userId,
                            String userPassword){

        // 아이디 이미 존재할시 다시 가입 페이지로
        if(memberRepository.findByUserId(userId).isPresent()){
            return "redirect:/signup";
        }

        var member = new Member();

        // Setter
        member.setUserId(userId);
        member.setUserPassword(passwordEncoder.encode(userPassword));
        member.setUserName(userName);
        member.setUserEmail(userEmail);

        // DB 저장
        memberRepository.save(member);

        return "redirect:/login";
    }

    // 마이 페이지 데이터 서비스
    public String getMyPage(Model model, Authentication auth){

        // 로그인 X일시 로그인 페이지로
        if(auth == null){ return "login.html";  }

        // 유저 정보 얻기
        MyUserDetailsService.CustomUser user = (MyUserDetailsService.CustomUser) auth.getPrincipal();

        List<Post> userPost =  postRepository.findByPostUserId(user.userId);


        String userName = user.getUsername();
        String userId = user.userId;

        model.addAttribute("userPost", userPost);
        model.addAttribute("userName", userName);
        model.addAttribute("userId", userId);

        return "mypage.html";
    }
}
