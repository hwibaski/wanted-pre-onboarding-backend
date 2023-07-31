package kr.co.wanted.backend.mypost.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.wanted.backend.mypost.controller.dto.request.PostSearch;
import kr.co.wanted.backend.mypost.domain.post.Post.Post;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.co.wanted.backend.mypost.domain.post.Post.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
}
