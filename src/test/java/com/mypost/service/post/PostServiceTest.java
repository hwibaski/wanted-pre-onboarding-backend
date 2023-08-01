package com.mypost.service.post;

import com.mypost.controller.dto.post.CreatePostRequestDto;
import com.mypost.controller.dto.post.EditPostRequestDto;
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
import java.util.Optional;
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

    private static final String TEST_USER_EMAIL = "test@gmail.com";
    private static final String ANOTHER_USER_EMAIL = "another@gmail.com";
    private static final String TEST_USER_PASSWORD = "12345678";

    @BeforeEach
    void beforeEach() {
        this.memberRepository.deleteAllInBatch();
        this.postRepository.deleteAllInBatch();
    }

    @DisplayName("post 작성 시 글을 작성한다.")
    @Test
    void createPostWhenSuccess() {
        // given
        Member memberForTest = createMemberForTest(TEST_USER_EMAIL, TEST_USER_PASSWORD);
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
        Member memberForTest = createMemberForTest(TEST_USER_EMAIL, TEST_USER_PASSWORD);
        List<Post> postsToSave = IntStream
                .rangeClosed(1, 10)
                .mapToObj(index -> Post.createPost("title " + index, "content " + index, memberForTest))
                .collect(toList());

        postRepository.saveAll(postsToSave);

        PostSearch postSearch = new PostSearch(1, 10);

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
        Member memberForTest = createMemberForTest(TEST_USER_EMAIL, TEST_USER_PASSWORD);
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
    }

    @DisplayName("post 수정")
    @Test
    void editPostWhenSuccess() {
        // given
        Member memberForTest = createMemberForTest(TEST_USER_EMAIL, TEST_USER_PASSWORD);
        Post post = Post.createPost("title", "content", memberForTest);
        Post savedPost = postRepository.save(post);

        String titleToUpdate = "title update";
        String contentToUpdate = "content update";

        EditPostRequestDto editPostRequestDto = new EditPostRequestDto(titleToUpdate, contentToUpdate);

        // when
        Post postToEdit = postService.editPostById(savedPost.getId(), editPostRequestDto, memberForTest);

        // then
        assertThat(postToEdit.getTitle()).isEqualTo(titleToUpdate);
        assertThat(postToEdit.getContent()).isEqualTo(contentToUpdate);
    }

    @DisplayName("post 수정 - 해당 게시글이 없을 때는 예외를 던진다")
    @Test
    void editPostWhenFailedToFindPost() {
        // given
        Member memberForTest = createMemberForTest(TEST_USER_EMAIL, TEST_USER_PASSWORD);
        Post post = Post.createPost("title", "content", memberForTest);
        Post savedPost = postRepository.save(post);

        String titleToUpdate = "title update";
        String contentToUpdate = "content update";
        EditPostRequestDto editPostRequestDto = new EditPostRequestDto(titleToUpdate, contentToUpdate);

        // when
        // then
        assertThatThrownBy(() -> postService.editPostById(1L, editPostRequestDto, memberForTest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 글을 찾을 수 없습니다.");
    }

    @DisplayName("post 수정 - 자신이 작성하지 않은 글은 수정할 수 없다.")
    @Test
    void editPostWhenFailedAuthorNotMatch() {
        // given
        String titleBeforeUpdate = "title";
        String contentBeforeUpdate = "content";
        Member memberForTest = createMemberForTest(TEST_USER_EMAIL, TEST_USER_PASSWORD);
        Post post = Post.createPost(titleBeforeUpdate, contentBeforeUpdate, memberForTest);
        Post savedPost = postRepository.save(post);

        Member anotherAuthor = createMemberForTest(ANOTHER_USER_EMAIL, TEST_USER_PASSWORD);

        String titleToUpdate = "title update";
        String contentToUpdate = "content update";

        EditPostRequestDto editPostRequestDto = new EditPostRequestDto(titleToUpdate, contentToUpdate);

        // when
        // then
        assertThatThrownBy(() -> postService.editPostById(savedPost.getId(), editPostRequestDto, anotherAuthor))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 글을 찾을 수 없습니다.");
    }

    @DisplayName("post 수정 - 수정할 항목에 null을 전달할 경우에는 해당 필드는 변경하지 않는다.")
    @Test
    void editPostWhenPassNull() {
        // given
        String titleBeforeUpdate = "title";
        String contentBeforeUpdate = "content";
        Member memberForTest = createMemberForTest(TEST_USER_EMAIL, TEST_USER_PASSWORD);
        Post post = Post.createPost(titleBeforeUpdate, contentBeforeUpdate, memberForTest);
        Post savedPost = postRepository.save(post);

        String titleToUpdate = null;
        String contentToUpdate = null;

        EditPostRequestDto editPostRequestDto = new EditPostRequestDto(titleToUpdate, contentToUpdate);

        // when
        Post postToEdit = postService.editPostById(savedPost.getId(), editPostRequestDto, memberForTest);

        // then
        assertThat(postToEdit.getTitle()).isEqualTo(titleBeforeUpdate);
        assertThat(postToEdit.getContent()).isEqualTo(contentBeforeUpdate);
    }

    @DisplayName("post 삭제")
    @Test
    void deletePostWhenSuccess() {
        // given
        Member memberForTest = createMemberForTest(TEST_USER_EMAIL, TEST_USER_PASSWORD);
        Post post = Post.createPost("title", "content", memberForTest);
        Post savedPost = postRepository.save(post);

        // when
        postService.deletePostById(savedPost.getId(), memberForTest);

        // then
        Optional<Post> postOptional = postRepository.findById(savedPost.getId());
        assertThat(postOptional.isPresent()).isEqualTo(false);
    }

    @DisplayName("post 삭제 - 해당 게시글이 없을 때는 예외를 던진다")
    @Test
    void deletePostWhenFailedToFindPost() {
        // given
        Member memberForTest = createMemberForTest(TEST_USER_EMAIL, TEST_USER_PASSWORD);

        // when
        // then
        assertThatThrownBy(() -> postService.deletePostById(1L, memberForTest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 글을 찾을 수 없습니다.");
    }

    @DisplayName("post 수정 - 자신이 작성하지 않은 글은 수정할 수 없다.")
    @Test
    void deletePostWhenFailedAuthorNotMatch() {
        // given
        String titleBeforeUpdate = "title";
        String contentBeforeUpdate = "content";
        Member memberForTest = createMemberForTest(TEST_USER_EMAIL, TEST_USER_PASSWORD);
        Post post = Post.createPost(titleBeforeUpdate, contentBeforeUpdate, memberForTest);
        Post savedPost = postRepository.save(post);

        Member anotherAuthor = createMemberForTest(ANOTHER_USER_EMAIL, TEST_USER_PASSWORD);

        // when
        // then
        assertThatThrownBy(() -> postService.deletePostById(savedPost.getId(), anotherAuthor))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 글을 찾을 수 없습니다.");
    }

    public Member createMemberForTest(String email, String password) {
        Member member = Member.createMember(email, password);
        return memberRepository.save(member);
    }
}
