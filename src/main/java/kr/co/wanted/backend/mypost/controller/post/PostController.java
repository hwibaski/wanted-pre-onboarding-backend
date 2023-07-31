package kr.co.wanted.backend.mypost.controller.post;

import kr.co.wanted.backend.mypost.controller.dto.post.CreatePostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Slf4j
public class PostController {
    @PostMapping("/post")
    public String post(@Valid @RequestBody CreatePostDto createPostDto) {
        return "post";
    }
}
