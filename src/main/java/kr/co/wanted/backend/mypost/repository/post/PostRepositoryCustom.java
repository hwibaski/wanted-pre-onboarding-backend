package kr.co.wanted.backend.mypost.repository.post;

import kr.co.wanted.backend.mypost.controller.dto.request.PostSearch;
import kr.co.wanted.backend.mypost.domain.post.Post.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
