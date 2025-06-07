package com.projetovirtus.api.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projetovirtus.api.Models.PostModel;

public interface PostRepository extends JpaRepository<PostModel, Long> {
    
    @Query("SELECT post FROM PostModel post WHERE post.title LIKE %:title%")
    List<PostModel> searchByTitle(@Param("title") String title);

}
