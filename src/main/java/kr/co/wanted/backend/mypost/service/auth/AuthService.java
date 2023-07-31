package kr.co.wanted.backend.mypost.service.auth;

import kr.co.wanted.backend.mypost.controller.dto.auth.AuthSignUpRequestDto;
import kr.co.wanted.backend.mypost.domain.member.Authority;
import kr.co.wanted.backend.mypost.domain.member.Member;
import kr.co.wanted.backend.mypost.domain.member.MemberAuthority;
import kr.co.wanted.backend.mypost.exception.AlreadyExistUserException;
import kr.co.wanted.backend.mypost.repository.member.AuthorityRepository;
import kr.co.wanted.backend.mypost.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member signUp(AuthSignUpRequestDto requestDto) {
        if (memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new AlreadyExistUserException("이미 존재하는 이메일입니다");
        }

        Optional<Authority> roleUserAuthority = authorityRepository.findAuthoritiesByAuthorityName("ROLE_USER");

        if (roleUserAuthority.isEmpty()) {
            throw new RuntimeException("ROLE_USER_NOT_FOUND");
        }

        MemberAuthority memberAuthority = MemberAuthority.createMemberAuthority(roleUserAuthority.get());

        Member member = Member.createMember(requestDto.getEmail(), passwordEncoder.encode(requestDto.getPassword()), memberAuthority);
        return memberRepository.save(member);
    }
}
