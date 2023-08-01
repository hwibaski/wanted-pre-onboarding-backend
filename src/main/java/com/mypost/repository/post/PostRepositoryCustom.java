package com.mypost.repository.post;

import com.mypost.controller.dto.request.PostSearch;
import com.mypost.domain.post.Post.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
