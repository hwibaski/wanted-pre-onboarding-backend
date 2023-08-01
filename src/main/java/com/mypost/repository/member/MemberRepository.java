package com.mypost.repository.member;

import com.mypost.domain.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = "memberAuthorities")
    Optional<Member> findMemberWithAuthoritiesByEmail(String email);
}
