package com.example.clone.Comment;

import com.example.clone.Member.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    // 상세창에서 댓글 조회
    @GetMapping("/getcomment/{id}")
    public ResponseEntity<List<Comment>> getComment(@PathVariable Long id) {

        List<Comment> comments = commentRepository.findByPostId(id);

        System.out.println(comments);


        return ResponseEntity.ok(comments);
    }

    // 댓글 저장
    @PostMapping ("/comment")
    public ResponseEntity<String> comment(@RequestBody Map<String, String> requestData,
                                          Authentication auth) {

        if(auth == null) {
            return ResponseEntity.ok("로그인해라");
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
