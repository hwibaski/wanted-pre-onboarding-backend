package com.mypost.service.post;

import com.mypost.controller.dto.post.CreatePostRequestDto;
import com.mypost.controller.dto.request.PostSearch;
import com.mypost.domain.member.Member;
import com.mypost.domain.post.Post.Post;
import com.mypost.exception.NotFoundException;
import com.mypost.repository.member.MemberRepository;
import com.mypost.repository.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void beforeEach() {
        this.memberRepository.deleteAllInBatch();
        this.postRepository.deleteAllInBatch();
    }

    @DisplayName("post 작성 시 글을 작성한다.")
    @Test
    void createPostWhenSuccess() {
        // given
        Member memberForTest = createMemberForTest();
        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto("title", "content");

        // when
        Post post = postService.create(createPostRequestDto, memberForTest);

        // then
        assertThat(post.getTitle()).isEqualTo(createPostRequestDto.getTitle());
        assertThat(post.getContent()).isEqualTo(createPostRequestDto.getContent());
        assertThat(post.getMember().getId()).isEqualTo(memberForTest.getId());
    }

    @DisplayName("post list를 조회한다.")
    @Test
    void getPostListWhenSuccess() {
        // given
        Member memberForTest = createMemberForTest();
        List<Post> postsToSave = IntStream
                .rangeClosed(1, 10)
                .mapToObj(index -> Post.createPost("title " + index, "content " + index, memberForTest))
                .collect(toList());

        postRepository.saveAll(postsToSave);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        // when
        List<Post> list = postService.getList(postSearch);

        // then
        assertThat(list.size()).isEqualTo(10);
        assertThat(list.get(0).getTitle()).isEqualTo("title 10");
    }

    @DisplayName("post 단건을 조회한다.")
    @Test
    void getPostWhenSuccess() {
        // given
        Member memberForTest = createMemberForTest();
        List<Post> postsToSave = IntStream
                .rangeClosed(1, 2)
                .mapToObj(index -> Post.createPost("title " + index, "content " + index, memberForTest))
                .collect(toList());

        postRepository.saveAll(postsToSave);

        // when
        Post post = postService.getPostById(postsToSave.get(0).getId());

        // then
        assertThat(post.getTitle()).isEqualTo("title 1");
    }

    @DisplayName("post가 없을 시 예외를 던진다.")
    @Test
    void getPostWhenFailedToFind() {
        // given

        // when
        assertThatThrownBy(() -> {
            postService.getPostById(10L);
        }).isInstanceOf(NotFoundException.class)
                .hasMessage("해당 글을 찾을 수 없습니다.");

        // then
    }

    public Member createMemberForTest() {
        Member member = Member.createMember("test@gmail.com", "12345678");
        return memberRepository.save(member);
    }
}
