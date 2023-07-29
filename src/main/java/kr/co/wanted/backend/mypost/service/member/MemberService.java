package kr.co.wanted.backend.mypost.service.member;

import kr.co.wanted.backend.mypost.controller.dto.member.MemberSignUpRequestDto;
import kr.co.wanted.backend.mypost.domain.member.Member;
import kr.co.wanted.backend.mypost.exception.AlreadyExistUserException;
import kr.co.wanted.backend.mypost.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member signUp(MemberSignUpRequestDto requestDto) {
        if (memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new AlreadyExistUserException("이미 존재하는 이메일입니다");
        }

        requestDto.encodePassword(passwordEncoder);
        return memberRepository.save(requestDto.toEntity());
    }
}
