package kr.co.wanted.backend.mypost.repository.member;

import kr.co.wanted.backend.mypost.domain.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = "memberAuthorities")
    Optional<Member> findMemberWithAuthoritiesByEmail(String email);
}
