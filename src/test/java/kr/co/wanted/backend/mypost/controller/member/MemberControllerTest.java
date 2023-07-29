package kr.co.wanted.backend.mypost.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.wanted.backend.mypost.controller.dto.member.MemberSignUpRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입")
    void memberSignUp() throws Exception {
        String email = "khmin3011@gmail.com";
        String password = "12345678";
        String requestBody = objectMapper.writeValueAsString(new MemberSignUpRequestDto(email, password));

        mockMvc.perform(post("/api/v1/member/signup")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.data.memberId").value(1L));
    }

    @Test
    @DisplayName("회원가입 실패 - email 공백일 시")
    void memberSignUpFailWhenEmailBlank() throws Exception {
        String email = "";
        String password = "12345678";
        String requestBody = objectMapper.writeValueAsString(new MemberSignUpRequestDto(email, password));

        mockMvc.perform(post("/api/v1/member/signup")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("이메일 입력해주세요"))
                .andExpect(jsonPath("$.data.memberId").doesNotExist());
    }

    @Test
    @DisplayName("회원가입 실패 - email 형식이 올바르지 않을 시")
    void memberSignUpFailWhenNotEmailForm() throws Exception {
        String email = "khmin3011naver.com";
        String password = "12345678";
        String requestBody = objectMapper.writeValueAsString(new MemberSignUpRequestDto(email, password));

        mockMvc.perform(post("/api/v1/member/signup")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("이메일 형식을 확인해주세요"))
                .andExpect(jsonPath("$.data.memberId").doesNotExist());
    }

    @Test
    @DisplayName("회원가입 실패 - password가 8글자보다 짧을 시")
    void memberSignUpFailWhenInvalidPassword() throws Exception {
        String email = "khmin3011@naver.com";
        String password = "1234567";
        String requestBody = objectMapper.writeValueAsString(new MemberSignUpRequestDto(email, password));

        mockMvc.perform(post("/api/v1/member/signup")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("비밀번호는 최소 8자 이상이여야합니다."))
                .andExpect(jsonPath("$.data.memberId").doesNotExist());
    }
}
