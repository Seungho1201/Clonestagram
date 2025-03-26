package com.example.clone;

import com.example.clone.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var result = memberRepository.findByUserId(username);


        if (result.isEmpty()){
            throw new UsernameNotFoundException("존재하지 않는 ID");
        }

        var user = result.get();

        // 권한 부여 위한 리스트
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 권한 부여
        authorities.add(new SimpleGrantedAuthority("normal"));

        var customUser = new CustomUser(user.getUserName(),
                                                user.getUserPassword(),
                                                authorities);

        // 아이디 받을수있게 커스텀
        customUser.userId = user.getUserId();

        return customUser;
    }

    // 커스텀 유저
    public class CustomUser extends User {

        public String userId;

        public CustomUser(String username,
                          String password,
                          List<GrantedAuthority> authorities ) {
            super(username, password, authorities);
        }
    }
}
