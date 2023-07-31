package kr.co.wanted.backend.mypost.repository.member;

import kr.co.wanted.backend.mypost.domain.member.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findAuthoritiesByAuthorityName(String authorityName);
}
