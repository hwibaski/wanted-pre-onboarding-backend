package com.mypost.service.member;

import com.mypost.domain.member.Member;
import com.mypost.exception.NotFoundException;
import com.mypost.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 회원을 찾을 수 없습니다."));
    }
}
