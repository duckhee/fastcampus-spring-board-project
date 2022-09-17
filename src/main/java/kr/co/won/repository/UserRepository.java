package kr.co.won.repository;

import kr.co.won.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDomain, String> {

    Optional<UserDomain> findByUserId(String username);

    Optional<UserDomain> findById(String userId);
}
