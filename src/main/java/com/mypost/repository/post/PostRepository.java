package com.mypost.repository.post;

import com.mypost.domain.member.Member;
import com.mypost.domain.post.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    Optional<Post> findPostByIdAndMember(Long postId, Member member);
}
