package kr.co.wanted.backend.mypost.service.post;

import kr.co.wanted.backend.mypost.controller.dto.post.CreatePostRequestDto;
import kr.co.wanted.backend.mypost.domain.member.Member;
import kr.co.wanted.backend.mypost.domain.post.Post.Post;
import kr.co.wanted.backend.mypost.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post create(CreatePostRequestDto createPostRequestDto, Member member) {
        Post post = Post.createPost(createPostRequestDto.getTitle(), createPostRequestDto.getContent(), member);

        return postRepository.save(post);
    }
}
