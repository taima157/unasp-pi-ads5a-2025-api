package com.projetovirtus.api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetovirtus.api.Models.PostModel;
import com.projetovirtus.api.Services.PostService;

@RestController
@RequestMapping("/post/solution")
public class SolutionController {
    
    @Autowired
    private PostService postService;

    @PutMapping("/{userId}/{postId}/{commentaryId}")
    public ResponseEntity<?> setCommentaryByIdAsSolution(@PathVariable Long userId, @PathVariable Long postId, @PathVariable Long commentaryId) {
        PostModel postModel = postService.getPostById(postId);
        postService.setCommentaryAsSolution(userId, commentaryId, postModel);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<?> removeSolutionFromPost(@PathVariable Long userId, @PathVariable Long postId) {
        PostModel postModel = postService.getPostById(postId);
        postService.removeSolutionFromPost(userId, postModel);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
