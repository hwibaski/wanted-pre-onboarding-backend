package kr.co.wanted.backend.mypost.service.post;

import kr.co.wanted.backend.mypost.controller.dto.post.CreatePostRequestDto;
import kr.co.wanted.backend.mypost.domain.member.Member;
import kr.co.wanted.backend.mypost.domain.post.Post.Post;
import kr.co.wanted.backend.mypost.repository.member.MemberRepository;
import kr.co.wanted.backend.mypost.repository.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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

    public Member createMemberForTest() {
        Member member = Member.createMember("test@gmail.com", "12345678");
        return memberRepository.save(member);
    }

}
