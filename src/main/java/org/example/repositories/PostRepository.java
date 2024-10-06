package org.example.repositories;

import org.example.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

//    @Query("SELECT p FROM Post p WHERE p.id = :postId")
//    Post findByIdWithoutUser(int postId);

}
