package com.projetovirtus.api.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projetovirtus.api.Exception.NotFoundException;
import com.projetovirtus.api.Exception.UnauthorizedException;
import com.projetovirtus.api.Models.CommentModel;
import com.projetovirtus.api.Models.PostModel;
import com.projetovirtus.api.Models.UserModel;
import com.projetovirtus.api.Repository.PostRepository;
import com.projetovirtus.api.Repository.UserRepository;
import com.projetovirtus.api.ViewObject.CommentViewObject;
import com.projetovirtus.api.ViewObject.PostViewObject;
import com.projetovirtus.api.ViewObject.SpceificPostViewObject;
import com.projetovirtus.api.ViewObject.UserViewObject;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CaseService caseService;

    public PostViewObject postModelToViewObject(PostModel postModel) {
        UserViewObject userModel = userService.getUserById(postModel.getPostOwner());

        PostViewObject postViewObject = new PostViewObject();
        postViewObject.setCaseData(caseService.getCaseById(postModel.getCaseId()));
        postViewObject.setDescription(postModel.getDescription());
        postViewObject.setId(postModel.getId());
        postViewObject.setProfissionalNeeded(postModel.getProfessionalNeeded());
        postViewObject.setTitle(postModel.getTitle());
        postViewObject.setUser(userModel);
        postViewObject.setCreatedAt(postModel.getCreatedAt());
        if (postModel.getSolution() != null) {
            postViewObject.setSolution(true);
        }

        return postViewObject;
    }

    public SpceificPostViewObject postModelToSpecificViewObject(PostModel postModel) {
        SpceificPostViewObject spceificPostViewObject = new SpceificPostViewObject();

        UserViewObject existingUser;
        try {
            existingUser = userService.getUserById(postModel.getPostOwner());
        } catch (Error error) {
            existingUser = new UserViewObject();
        }

        List<CommentModel> commentModels = postModel.getComments();
        List<CommentViewObject> commentViewObjects = new ArrayList<>();

        for (CommentModel commentModel : commentModels) {
            CommentViewObject commentViewObject = commentModelToViewObject(commentModel);
            commentViewObjects.add(commentViewObject);
        }

        spceificPostViewObject.setComments(commentViewObjects);
        spceificPostViewObject.setDescription(postModel.getDescription());
        spceificPostViewObject.setId(postModel.getId());
        spceificPostViewObject.setProfissionalNeeded(postModel.getProfessionalNeeded());
        spceificPostViewObject.setTitle(postModel.getTitle());
        spceificPostViewObject.setCreatedAt(LocalDateTime.now());
        spceificPostViewObject.setPostOwner(existingUser);

        Integer postIdLongToInteger = Math.toIntExact(postModel.getCaseId());
        spceificPostViewObject.setCaseData(caseService.getCaseById(postIdLongToInteger));

        if (postModel.getSolution() != null) {
            spceificPostViewObject.setSolution(commentModelToViewObject(postModel.getSolution()));
        }

        return spceificPostViewObject;
    }

    public CommentViewObject commentModelToViewObject(CommentModel commentModel) {
        CommentViewObject commentViewObject = new CommentViewObject();

        UserViewObject userViewObject;

        if (commentModel.getUser() != null) {
            userViewObject = userService.userModelToViewObject(commentModel.getUser());
        } else {
            userViewObject = new UserViewObject();
        }

        commentViewObject.setContent(commentModel.getContent());
        commentViewObject.setCreatedAt(commentModel.getCreatedAt());
        commentViewObject.setEdited(commentModel.getEdited());
        commentViewObject.setId(commentModel.getId());
        commentViewObject.setUser(userViewObject);

        return commentViewObject;
    }

    public PostModel newPost(PostModel postInput) {
        long userId = postInput.getPostOwner();
        Optional<UserModel> existingUser = Optional
                .of(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuário não encontrado")));

        PostModel newPost = new PostModel();
        newPost.setPostOwner(existingUser.get().getId());
        newPost.setCaseId(postInput.getCaseId());
        newPost.setDescription(postInput.getDescription());
        newPost.setProfessionalNeeded(postInput.getProfessionalNeeded());
        newPost.setTitle(postInput.getTitle());
        newPost.setCreatedAt(LocalDateTime.now());

        postRepository.save(newPost);

        return newPost;
    }

    public void editPost(Long userId, PostModel postModel, PostModel postInput) {
        if (userId != postModel.getPostOwner()) {
            throw new UnauthorizedException("Operação não autorizada");
        }

        postModel.setTitle(postInput.getTitle());
        postModel.setDescription(postInput.getDescription());

        postRepository.save(postModel);
    }

    public void deletePost(Long userId, Long postId, PostModel postModel) {
        if (userId != postModel.getPostOwner()) {
            throw new UnauthorizedException("Operação não autorizada");
        }

        postRepository.delete(postModel);
    }

    public void setCommentaryAsSolution(Long userId, Long commentaryId, PostModel postModel) {
        if (userId != postModel.getPostOwner()) {
            throw new UnauthorizedException("Operação não autorizada");
        }

        Boolean beenFound = false;

        List<CommentModel> commentModelList = postModel.getComments();
        for (CommentModel commentModel : commentModelList) {
            if (commentModel.getId() == commentaryId) {
                postModel.setSolution(commentModel);
                beenFound = true;
                break;
            }
        }

        if (!beenFound) {
            throw new NotFoundException("Comentário não encontrado");
        }

        postRepository.save(postModel);
    }

    public void removeSolutionFromPost(Long userId, PostModel postModel) {
        if (userId != postModel.getPostOwner()) {
            throw new UnauthorizedException("Operação não autorizada");
        }

        postModel.setSolution(null);
        postRepository.save(postModel);
    }

    public List<PostViewObject> getAllPosts() {
        return postRepository.findAll().stream().map(this::postModelToViewObject).toList();
    }

    public Page<PostViewObject> getAllPostPaginated(Pageable pageable, String search, List<Integer> caseId) {
        Supplier<Stream<PostViewObject>> streamSupplier = () -> postRepository.searchByTitle(search)
                .stream()
                .map(this::postModelToViewObject);

        long total = streamSupplier.get()
                .filter(post -> caseId == null || caseId.isEmpty()
                        || caseId.stream().anyMatch(id -> id.equals(post.getCaseData().getCaseId())))
                .count();

        return streamSupplier.get()
                .filter(post -> caseId == null || caseId.isEmpty()
                        || caseId.stream().anyMatch(id -> id.equals(post.getCaseData().getCaseId())))
                .sorted(Comparator.comparing(PostViewObject::getCreatedAt).reversed())
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, total)));
    }

    public List<PostViewObject> searchPostByTitle(String title) {
        return postRepository.searchByTitle(title).stream().map(this::postModelToViewObject).toList();
    }

    public PostModel getPostById(Long id) {
        Optional<PostModel> existingPost = Optional
                .of(postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post não encontrado")));
        return existingPost.get();
    }

    public SpceificPostViewObject getSpecificPostById(Long id) {
        Optional<PostModel> existingPost = Optional
                .of(postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post não encontrado")));
        return postModelToSpecificViewObject(existingPost.get());
    }
}
