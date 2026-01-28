package org.example.demo.Service;

import org.example.demo.Model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes,Long> {
    Optional<Likes> findByUserIdAndThoughtId(Long userId, Long thoughtId);
}
