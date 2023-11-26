package net.fernandosalas.springbootblogpost.repository;

import net.fernandosalas.springbootblogpost.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
