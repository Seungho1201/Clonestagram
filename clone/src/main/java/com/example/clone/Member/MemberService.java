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

        // 멤버 객체 생성
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

        // 로그아웃시 로그인 페이지로
        if(auth == null){ return "login.html";  }

        // 유저 정보 얻기
        MyUserDetailsService.CustomUser user = (MyUserDetailsService.CustomUser) auth.getPrincipal();

        // DTO
        MemberDTO memberDTO = new MemberDTO();

        // Setter
        memberDTO.setUserId(user.userId);
        memberDTO.setUsername(user.getUsername());
        memberDTO.setPostUserData(postRepository.findByPostUserId(user.userId));

        // 데이터 전송
        model.addAttribute("userPost", memberDTO.getPostUserData());
        model.addAttribute("userName", memberDTO.getUserId());
        model.addAttribute("userId", memberDTO.getUserId());

        return "mypage.html";
    }
}
