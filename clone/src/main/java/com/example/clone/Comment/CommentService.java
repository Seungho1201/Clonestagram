package com.example.clone.Comment;

import com.example.clone.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    // 댓글 가져오는 서비스
    public ResponseEntity<List<Comment>> commentGet(Long id){

        List<Comment> comments = commentRepository.findByPostId(id);

        return ResponseEntity.ok(comments);
    }

    // 댓글 저장 서비스
    public ResponseEntity<String> commentSave(Map<String, String> requestData,
                                              Authentication auth){
        if(auth == null) {
            return ResponseEntity.ok("로그인필요");
        }

        // 유저 정보 GET
        MyUserDetailsService.CustomUser customUser = (MyUserDetailsService.CustomUser) auth.getPrincipal();

        // Body에서 데이터 가져오기
        String postId = requestData.get("postId");
        String commentContent = requestData.get("commentContent");


        // Comment 객체 생성
        Comment comment = new Comment();

        comment.setPost_id(Long.valueOf(postId));
        comment.setCommentContent(commentContent);
        comment.setUserId(customUser.userId);

        // DB 저장
        commentRepository.save(comment);

        return ResponseEntity.ok("댓글 추가 완료");
    }

}
