package kr.co.wanted.backend.mypost.service.post;

import kr.co.wanted.backend.mypost.controller.dto.post.CreatePostDto;
import kr.co.wanted.backend.mypost.domain.post.Post.Post;
import kr.co.wanted.backend.mypost.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post create(CreatePostDto createPostDto) {
        Post post = Post.createPost(createPostDto.getTitle(), createPostDto.getContent());

        return postRepository.save(post);
    }
}
