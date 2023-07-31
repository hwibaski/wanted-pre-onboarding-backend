package kr.co.wanted.backend.mypost.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.wanted.backend.mypost.controller.dto.auth.AuthSignInRequestDto;
import kr.co.wanted.backend.mypost.controller.dto.auth.AuthSignUpRequestDto;
import kr.co.wanted.backend.mypost.domain.member.Authority;
import kr.co.wanted.backend.mypost.domain.member.Member;
import kr.co.wanted.backend.mypost.domain.member.MemberAuthority;
import kr.co.wanted.backend.mypost.repository.member.AuthorityRepository;
import kr.co.wanted.backend.mypost.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach() {
        this.memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원가입")
    void memberSignUp() throws Exception {
        String email = "khmin3011@gmail.com";
        String password = "12345678";
        String requestBody = objectMapper.writeValueAsString(new AuthSignUpRequestDto(email, password));

        mockMvc.perform(post("/api/v1/member/signup")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").exists());
    }

    @Test
    @DisplayName("회원가입 실패 - email 공백일 시")
    void memberSignUpFailWhenEmailBlank() throws Exception {
        //given
        String email = "";
        String password = "12345678";
        String requestBody = objectMapper.writeValueAsString(new AuthSignUpRequestDto(email, password));

        // when
        // then
        mockMvc.perform(post("/api/v1/member/signup")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.email").value("이메일 입력해주세요"));
    }

    @Test
    @DisplayName("회원가입 실패 - email 형식이 올바르지 않을 시")
    void memberSignUpFailWhenNotEmailForm() throws Exception {
        // given
        String email = "khmin3011naver.com";
        String password = "12345678";
        String requestBody = objectMapper.writeValueAsString(new AuthSignUpRequestDto(email, password));

        // when
        // then
        mockMvc.perform(post("/api/v1/member/signup")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.email").value("이메일 형식을 확인해주세요"));
    }

    @Test
    @DisplayName("회원가입 실패 - password가 8글자보다 짧을 시")
    void memberSignUpFailWhenInvalidPassword() throws Exception {
        // given
        String email = "khmin3011@naver.com";
        String password = "1234567";
        String requestBody = objectMapper.writeValueAsString(new AuthSignUpRequestDto(email, password));

        // when
        // then
        mockMvc.perform(post("/api/v1/member/signup")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.password").value("비밀번호는 최소 8자 이상이여야합니다."));
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 email 존재할 시")
    void memberSignUpFailWhenDuplicatedEmail() throws Exception {
        // given
        String email = "khmin3011@gmail.com";
        String password = "12345678";
        AuthSignUpRequestDto authSignUpRequestDto = new AuthSignUpRequestDto(email, password);
        Optional<Authority> authority = authorityRepository.findAuthoritiesByAuthorityName("ROLE_USER");
        MemberAuthority memberAuthority = MemberAuthority.createMemberAuthority(authority.get());
        Member member = Member.createMember(authSignUpRequestDto.getEmail(), passwordEncoder.encode(authSignUpRequestDto.getPassword()), memberAuthority);
        memberRepository.save(member);

        String requestBody = objectMapper.writeValueAsString(authSignUpRequestDto);

        // when
        // then
        mockMvc.perform(post("/api/v1/member/signup")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("이미 존재하는 이메일입니다"));
    }

    @Test
    @DisplayName("회원 로그인")
    void memberSignIn() throws Exception {
        // given
        String email = "khmin3011@gmail.com";
        String password = "12345678";
        AuthSignUpRequestDto authSignUpRequestDto = new AuthSignUpRequestDto(email, password);
        Optional<Authority> authority = authorityRepository.findAuthoritiesByAuthorityName("ROLE_USER");
        MemberAuthority memberAuthority = MemberAuthority.createMemberAuthority(authority.get());
        Member member = Member.createMember(authSignUpRequestDto.getEmail(), passwordEncoder.encode(authSignUpRequestDto.getPassword()), memberAuthority);

        memberRepository.save(member);

        AuthSignInRequestDto authSignInRequestDto = new AuthSignInRequestDto(email, password);
        String requestBody = objectMapper.writeValueAsString(authSignInRequestDto);

        // when
        // then
        mockMvc.perform(post("/api/v1/member/signin")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.accessToken").isString());
    }

    @Test
    @DisplayName("회원 로그인 - 로그인 실패")
    void memberSignInFail() throws Exception {
        // given
        String email = "khmin3011@gmail.com";
        String password = "12345678";
        AuthSignUpRequestDto authSignUpRequestDto = new AuthSignUpRequestDto(email, password);
        Optional<Authority> authority = authorityRepository.findAuthoritiesByAuthorityName("ROLE_USER");
        MemberAuthority memberAuthority = MemberAuthority.createMemberAuthority(authority.get());
        Member member = Member.createMember(authSignUpRequestDto.getEmail(), passwordEncoder.encode(authSignUpRequestDto.getPassword()), memberAuthority);

        memberRepository.save(member);

        String inValidPassword = "11111111";
        AuthSignInRequestDto authSignInRequestDto = new AuthSignInRequestDto(email, inValidPassword);
        String requestBody = objectMapper.writeValueAsString(authSignInRequestDto);

        // when
        // then
        mockMvc.perform(post("/api/v1/member/signin")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("401"))
                .andExpect(jsonPath("$.message").value("이메일과 비밀번호를 확인해주세요"));
    }
}
