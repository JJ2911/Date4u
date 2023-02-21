package com.tutego.date4u.core.profile.like;

import com.tutego.date4u.core.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Set;

public interface LikesRepository extends JpaRepository<Likes,LikesId>, QuerydslPredicateExecutor {
  Set<Likes> findByLikee(Profile profile);
  Set<Likes> findByLiker(Profile profile);
}
