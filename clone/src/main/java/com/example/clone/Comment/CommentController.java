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
    private final CommentService commentService;

    // 상세창에서 댓글 조회
    @GetMapping("/getcomment/{id}")
    public ResponseEntity<List<Comment>> getComment(@PathVariable Long id) {

        // Service 레이어
        return commentService.commentGet(id);
    }

    // 댓글 저장
    @PostMapping ("/comment")
    public ResponseEntity<String> comment(@RequestBody Map<String, String> requestData,
                                          Authentication auth) {

        // Service 레이어
        return commentService.commentSave(requestData, auth);
    }
}
