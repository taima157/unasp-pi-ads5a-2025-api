package com.projetovirtus.api.Services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetovirtus.api.Exception.NotFoundException;
import com.projetovirtus.api.Exception.UnauthorizedException;
import com.projetovirtus.api.Models.CommentModel;
import com.projetovirtus.api.Models.PostModel;
import com.projetovirtus.api.Models.UserModel;
import com.projetovirtus.api.Repository.PostRepository;
import com.projetovirtus.api.ViewObject.UserViewObject;

@Service
public class CommentService {
    
    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    public CommentModel getCommentaryById(PostModel postModel, Long commentaryId) {
        CommentModel existingComment = null;

        for (CommentModel commentModel : postModel.getComments()) {
            if (commentModel.getId() == commentaryId) {
                existingComment = commentModel;
                break;
            }
        }

        if (existingComment == null) {
            throw new NotFoundException("Comentário não existe");
        }

        return existingComment;
    }

    public void addCommentaryToPost(Long postId, Long userId, CommentModel comment) {
        PostModel presumedPost = postService.getPostById(postId);
        UserViewObject existingUser = userService.getUserById(userId);
        UserModel userObject = userService.userViewObjectToModel(existingUser);

        CommentModel newCommentary = new CommentModel();
        newCommentary.setCreatedAt(LocalDateTime.now());
        newCommentary.setContent(comment.getContent());
        newCommentary.setUser(userObject);

        presumedPost.getComments().add(newCommentary);

        postRepository.save(presumedPost);
    }

    public void editCommentary(Long postId, Long userId, Long commentaryId, CommentModel comment) {
        PostModel presumedPost = postService.getPostById(postId);
        CommentModel existingCommentary = getCommentaryById(presumedPost, commentaryId);

        if (userId != existingCommentary.getUser().getId()) {
            throw new UnauthorizedException("Operação não autorizada");
        }

        existingCommentary.setContent(comment.getContent());
        existingCommentary.setEdited(true);

        postRepository.save(presumedPost);
    }

    public void removeCommentary(Long postId, Long userId, Long commentaryId) {
        PostModel presumedPost = postService.getPostById(postId);
        CommentModel existingCommentary = getCommentaryById(presumedPost, commentaryId);

        if (userId != existingCommentary.getUser().getId()) {
            throw new UnauthorizedException("Operação não autorizada");
        }

        presumedPost.getComments().remove(existingCommentary);
        postRepository.save(presumedPost);
    }
}
