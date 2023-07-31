package kr.co.wanted.backend.mypost.controller.post;

import kr.co.wanted.backend.mypost.controller.dto.post.CreatePostDto;
import kr.co.wanted.backend.mypost.domain.post.Post.Post;
import kr.co.wanted.backend.mypost.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public String post(@Valid @RequestBody CreatePostDto createPostDto) {
        Post post = postService.create(createPostDto);
        return "post";
    }
}
