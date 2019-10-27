package com.accenture.library.repository;

import com.accenture.library.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findAuthoritiesByAuthorityName(String authority);
}
