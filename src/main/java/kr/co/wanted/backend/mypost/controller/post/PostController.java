package kr.co.wanted.backend.mypost.controller.post;

import kr.co.wanted.backend.mypost.controller.dto.post.CreatePostRequestDto;
import kr.co.wanted.backend.mypost.controller.dto.post.CreatePostResponseDto;
import kr.co.wanted.backend.mypost.domain.member.Member;
import kr.co.wanted.backend.mypost.domain.post.Post.Post;
import kr.co.wanted.backend.mypost.service.member.MemberService;
import kr.co.wanted.backend.mypost.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePostResponseDto post(@Valid @RequestBody CreatePostRequestDto createPostRequestDto, Principal principal) {
        Member member = memberService.findMemberByEmail(principal.getName());
        Post post = postService.create(createPostRequestDto, member);
        return new CreatePostResponseDto(post.getId());
    }
}
