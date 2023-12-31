package com.mypost.service.post;

import com.mypost.controller.dto.post.CreatePostRequestDto;
import com.mypost.controller.dto.post.EditPostRequestDto;
import com.mypost.controller.dto.request.PostSearch;
import com.mypost.domain.member.Member;
import com.mypost.domain.post.Post.Post;
import com.mypost.exception.NotFoundException;
import com.mypost.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post create(CreatePostRequestDto createPostRequestDto, Member member) {
        Post post = Post.createPost(createPostRequestDto.getTitle(), createPostRequestDto.getContent(), member);

        return postRepository.save(post);
    }

    public List<Post> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch);
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("해당 글을 찾을 수 없습니다."));
    }

    @Transactional
    public Post editPostById(Long postId, EditPostRequestDto editPostRequestDto, Member member) {
        Post post = postRepository.findPostByIdAndMember(postId, member)
                .orElseThrow(() -> new NotFoundException("해당 글을 찾을 수 없습니다."));

        post.change(editPostRequestDto.getTitle(), editPostRequestDto.getContent());
        return post;
    }

    @Transactional
    public void deletePostById(Long postId, Member member) {
        Post post = postRepository.findPostByIdAndMember(postId, member)
                .orElseThrow(() -> new NotFoundException("해당 글을 찾을 수 없습니다."));

        postRepository.delete(post);
    }
}
