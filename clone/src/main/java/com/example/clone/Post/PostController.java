package com.example.clone.Post;

import com.example.clone.Member.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    // 메인 게시글 API
    @GetMapping("/main")
    String pistList(Model model) {
        List<Post> postData2 = postRepository.findAll();
        model.addAttribute("postData2", postData2);

        return "main.html";
    }

    // 현재 테스트 용으로 쓰고있는 메인 페이지
    @GetMapping("/test")
    String testList(Model model, Authentication auth) {

        // 게시글 리스트 페이지 전송
        List<Post> postData2 = postService.sendPostList();

        // 로그인 상태 아니여도 접속 가능
        model.addAttribute("postData2", postData2);

        // 로그아웃 상태일시
        if(auth == null){
            model.addAttribute("userName", "오프라인");
            model.addAttribute("userId", "오프라인");

            return "test.html";
        }

        // 로그인 상태일 시 메인 페이지로 전송할 유저 데이터
        MyUserDetailsService.CustomUser user = postService.sendUserData(auth);

        model.addAttribute("userName", user.getUsername());
        model.addAttribute("userId", user.userId);

        return "test.html";
    }

    @GetMapping("/post")
    String goPost(Model model, Authentication auth) {

        // 로그아웃 상태일 시 로그인 페이지로 보냄
        if(auth == null){ return "redirect:/login"; }

        // 유저 데이터 가져오는 서비스 재활용
        MyUserDetailsService.CustomUser user = postService.sendUserData(auth);

        // 포스트 페이지에 필요한 유저데이터는 아이디만
        model.addAttribute("userId", user.userId);

        return "post.html";
    }

    // 게시글 포스팅 기능
    @PostMapping("/postfunc")
    String goPostFunc(Model model, Authentication auth,
                      @RequestParam String postData) {
        // 유저 데이터 가져오는 서비스 재활용
        MyUserDetailsService.CustomUser user = postService.sendUserData(auth);

        // 게시글 업로드 서비스
        int result = postService.postFuncService(user, postData);

        if(result == 200){
            // 서비스 수행 후 메인 페이지로
            return "redirect:/test";
        } else{
            return "redirect:/post";
        }
    }

    // 상세 페이지 Ajax 응답 API
    @GetMapping("/postDetail/{id}")
    ResponseEntity<Post> postDetail(@PathVariable Long id){

        Post post = postRepository.findById(id).orElse(null);

        return ResponseEntity.ok(post); // JSON 응답 반환
    }

    // 수정 페이지
    @GetMapping("/edit/{id}")
    String editPost(@PathVariable Long id, Model model, Authentication auth) {

        // 해당 게시글 데이터 가져옴
        Post post = postRepository.findById(id).orElse(null);

        // 유저 데이터 가져오는 서비스 재활용
        MyUserDetailsService.CustomUser user = postService.sendUserData(auth);

        // 존재안할시 혹시 모르니 원래 페이지로
        if(post == null){ return "test"; }

        // 로그아웃 상태로 진입시 로그인 페이지로
        if(user.userId == null){ return "redirect:/login"; }

        // 게시글 작성자랑 수정하려는 id 다를시 원래 페이지로
        if(!user.userId.equals(post.getPostUserId())){
            System.out.println("누구새요?");
            return "test";
        }

        model.addAttribute("postData", post);
        model.addAttribute("userId", user.userId);

        return "editPost";
    }

    @PostMapping("/editpost")
    String editPost(@RequestParam String postData,
                    @RequestParam Long postId,
                    @RequestParam Long postRecommend,
                    @RequestParam String postUserId){

        Post post = new Post();

        post.setPostId(postId);
        post.setPostContent(postData);
        post.setPostUserId(postUserId);
        post.setPostRecommend(postRecommend);
        post.setPostImg("미구현");

        postRepository.save(post);


        return "redirect:/test";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, Authentication auth){

        MyUserDetailsService.CustomUser user = postService.sendUserData(auth);

        Post post = postRepository.findById(id).orElse(null);


        postRepository.deleteById(id);


        return ResponseEntity.ok("삭제 완료");
    }


}
