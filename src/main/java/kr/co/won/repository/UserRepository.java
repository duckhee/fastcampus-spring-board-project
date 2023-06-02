package kr.co.won.repository;

import kr.co.won.domain.UserDomain;
import kr.co.won.domain.projection.UserAccountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RepositoryRestResource(excerptProjection = UserAccountProjection.class)
public interface UserRepository extends JpaRepository<UserDomain, String> {

    Optional<UserDomain> findByUserId(String username);

    Optional<UserDomain> findById(String userId);
}
