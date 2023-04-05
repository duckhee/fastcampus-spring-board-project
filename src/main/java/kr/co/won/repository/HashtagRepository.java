package kr.co.won.repository;

import kr.co.won.domain.HashTagDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource
public interface HashtagRepository extends JpaRepository<HashTagDomain, Long>, QuerydslPredicateExecutor<HashTagDomain> {

    Optional<HashTagDomain> findByHashTagName(String name);

    List <HashTagDomain> findByHashTagNameIn(Set<String> names);

    List<String> findAllByHashTagName();
}
