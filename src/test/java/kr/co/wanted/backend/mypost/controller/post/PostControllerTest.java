package kr.co.wanted.backend.mypost.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.wanted.backend.mypost.annotation.WithAccount;
import kr.co.wanted.backend.mypost.controller.dto.post.CreatePostRequestDto;
import kr.co.wanted.backend.mypost.repository.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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

    @BeforeEach
    void beforeEach() {
        this.postRepository.deleteAllInBatch();
    }

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
}
