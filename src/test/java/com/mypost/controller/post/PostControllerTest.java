package com.mypost.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mypost.annotation.WithAccount;
import com.mypost.controller.dto.post.CreatePostRequestDto;
import com.mypost.domain.member.Member;
import com.mypost.domain.post.Post.Post;
import com.mypost.repository.member.MemberRepository;
import com.mypost.repository.post.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void afterEach() {
        this.postRepository.deleteAllInBatch();
        this.memberRepository.deleteAllInBatch();
    }

    @Nested
    class PostCreateForAvoidBeforeEach {
        @Test
        @DisplayName("글 작성 성공")
        @WithAccount("test@gmail.com")
        void createPost() throws Exception {
            // given
            String title = "글 제목";
            String content = "글 내용";
            CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto(title, content);
            String requestBody = objectMapper.writeValueAsString(createPostRequestDto);

            // when
            mockMvc.perform(post("/api/post")
                            .contentType(APPLICATION_JSON)
                            .content(requestBody)
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.postId").exists())
                    .andDo(print());

            // then
            assertThat(postRepository.count()).isEqualTo(1);
        }

    }

    @Test
    @DisplayName("글 작성 실패 - title 비어있을 시")
    @WithMockUser
    void createPostWhenTitleInvalid() throws Exception {
        // given
        String title = "";
        String content = "content";
        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto(title, content);
        String requestBody = objectMapper.writeValueAsString(createPostRequestDto);

        // when
        // then
        mockMvc.perform(post("/api/post")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("글 제목을 확인해주세요"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 작성 실패 - content 비어있을 시")
    @WithMockUser
    void createPostWhenContentInvalid() throws Exception {
        // given
        String title = "title";
        String content = "";
        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto(title, content);
        String requestBody = objectMapper.writeValueAsString(createPostRequestDto);

        // when
        // then
        mockMvc.perform(post("/api/post")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.content").value("글 본문을 확인해주세요"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 목록 조회")
    void getPostList() throws Exception {
        // given
        Member memberForTest = createMemberForTest();

        List<Post> postsToSave = IntStream
                .rangeClosed(1, 10)
                .mapToObj(index -> Post.createPost("title " + index, "content " + index, memberForTest))
                .collect(toList());

        postRepository.saveAll(postsToSave);

        // when
        // then
        mockMvc.perform(get("/api/posts")
                        .param("size", "10")
                        .param("page", "1")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$[0].title").value("title 10"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 목록 조회 - 페이지 파라미터를 0으로 요청하면 첫 페이지를 가져온다.")
    void getPostListWhenPageParamZero() throws Exception {
        // given
        Member memberForTest = createMemberForTest();

        List<Post> postsToSave = IntStream
                .rangeClosed(1, 10)
                .mapToObj(index -> Post.createPost("title " + index, "content " + index, memberForTest))
                .collect(toList());

        postRepository.saveAll(postsToSave);

        // when
        // then
        mockMvc.perform(get("/api/posts")
                        .param("size", "10")
                        .param("page", "0")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$[0].id").value("10"))
                .andDo(print());
    }


    public Member createMemberForTest() {
        Member member = Member.createMember("test@gmail.com", "12345678");
        return memberRepository.save(member);
    }
}
