package com.mypost.controller.post;

import com.mypost.controller.dto.post.CreatePostRequestDto;
import com.mypost.controller.dto.post.CreatePostResponseDto;
import com.mypost.controller.dto.post.GetPostsResponseDto;
import com.mypost.controller.dto.request.PostSearch;
import com.mypost.domain.member.Member;
import com.mypost.domain.post.Post.Post;
import com.mypost.service.member.MemberService;
import com.mypost.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<GetPostsResponseDto> getPostList(@ModelAttribute PostSearch postSearch) {
        return postService
                .getList(postSearch)
                .stream()
                .map(post -> new GetPostsResponseDto(post.getId(), post.getTitle(), post.getContent()))
                .collect(Collectors.toList());
    }
}
