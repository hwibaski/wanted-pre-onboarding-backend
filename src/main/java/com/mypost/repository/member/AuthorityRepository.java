package com.mypost.repository.member;

import com.mypost.domain.member.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findAuthoritiesByAuthorityName(String authorityName);
}
