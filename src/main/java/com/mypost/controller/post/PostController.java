package com.mypost.controller.post;

import com.mypost.controller.dto.post.CreatePostRequestDto;
import com.mypost.controller.dto.post.CreatePostResponseDto;
import com.mypost.controller.dto.post.EditPostRequestDto;
import com.mypost.controller.dto.post.EditPostResponseDto;
import com.mypost.controller.dto.post.GetPostResponseDto;
import com.mypost.controller.dto.request.PostSearch;
import com.mypost.domain.member.Member;
import com.mypost.domain.post.Post.Post;
import com.mypost.exception.UnathorizedException;
import com.mypost.service.member.MemberService;
import com.mypost.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        if (principal == null) {
            throw new UnathorizedException("인증 정보를 확인해주세요");
        }
        Member member = memberService.findMemberByEmail(principal.getName());
        Post post = postService.create(createPostRequestDto, member);
        return new CreatePostResponseDto(post.getId());
    }

    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<GetPostResponseDto> getPostList(@ModelAttribute PostSearch postSearch) {
        return postService
                .getList(postSearch)
                .stream()
                .map(post -> new GetPostResponseDto(post.getId(), post.getTitle(), post.getContent()))
                .collect(Collectors.toList());
    }

    @GetMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public GetPostResponseDto getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return new GetPostResponseDto(post.getId(), post.getTitle(), post.getContent());
    }

    @PatchMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public EditPostResponseDto editPostById(@PathVariable Long postId, @Valid @RequestBody EditPostRequestDto editPostRequestDto, Principal principal) {
        if (principal == null) {
            throw new UnathorizedException("인증 정보를 확인해주세요");
        }
        Member member = memberService.findMemberByEmail(principal.getName());
        Post post = postService.editPostById(postId, editPostRequestDto, member);
        return new EditPostResponseDto(post.getId());
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePostById(@PathVariable Long postId, Principal principal) {
        if (principal == null) {
            throw new UnathorizedException("인증 정보를 확인해주세요");
        }
        Member member = memberService.findMemberByEmail(principal.getName());
        postService.deletePostById(postId, member);
    }
}
