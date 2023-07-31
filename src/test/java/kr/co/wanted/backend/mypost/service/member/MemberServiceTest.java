package kr.co.wanted.backend.mypost.service.member;

import kr.co.wanted.backend.mypost.domain.member.Member;
import kr.co.wanted.backend.mypost.exception.NotFoundException;
import kr.co.wanted.backend.mypost.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;


    @BeforeEach
    void beforeEach() {
        this.memberRepository.deleteAllInBatch();
    }

    @DisplayName("email로 Member 객체를 조회한다.")
    @Test
    void findUserByEmailWhenSuccess() {
        // given
        Member member = createMemberForTest();

        // when
        Member findMember = memberService.findMemberByEmail(member.getEmail());

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
    }

    @DisplayName("존재하지 않는 Member를 조회하면 예외를 던진다.")
    @Test
    void findUserById2() {
        assertThatThrownBy(() -> {
            memberService.findMemberByEmail("notExistMember@gmail.com");
        })
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 회원을 찾을 수 없습니다.");
    }

    public Member createMemberForTest() {
        Member member = Member.createMember("test@gmail.com", "12345678");
        return memberRepository.save(member);
    }
}
