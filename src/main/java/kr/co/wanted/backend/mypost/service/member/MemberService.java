package kr.co.wanted.backend.mypost.service.member;

import kr.co.wanted.backend.mypost.domain.member.Member;
import kr.co.wanted.backend.mypost.exception.NotFoundException;
import kr.co.wanted.backend.mypost.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 회원을 찾을 수 없습니다."));
    }
}
