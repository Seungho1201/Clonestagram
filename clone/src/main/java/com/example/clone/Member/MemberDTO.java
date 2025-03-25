package com.example.clone.Member;

import com.example.clone.Post.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberDTO {
    // 유저들한테 보내줄 데이터는 유저 Id와 이름뿐
    private String userId;
    private String username;
    private List<Post> postUserData;
}
