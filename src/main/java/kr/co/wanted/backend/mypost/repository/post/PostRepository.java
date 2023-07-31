package kr.co.wanted.backend.mypost.repository.post;

import kr.co.wanted.backend.mypost.domain.post.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
