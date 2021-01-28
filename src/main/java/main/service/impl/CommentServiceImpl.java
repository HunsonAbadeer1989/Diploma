package main.service.impl;

import main.api.request.CommentRequest;
import main.api.response.CommentAddResponse;
import main.api.response.CommentErrorResponse;
import main.api.response.ResponseApi;
import main.model.Post;
import main.model.PostComment;
import main.model.User;
import main.repository.CommentRepository;
import main.repository.PostRepository;
import main.repository.UserRepository;
import main.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final UserRepository userRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ResponseApi> postComment(CommentRequest commentRequest, Principal principal) {
        PostComment newPostComment;
        HashMap<String, String> errors = new HashMap<>();

        User user = userRepository.findUserByEmail(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("user not found"));

        Post postById = postRepository.findById(commentRequest.getPostId());

        if (commentRequest.getText().length() < 1) {
            errors.put("text", "Text of comment is too short");
            CommentErrorResponse errorResponse = new CommentErrorResponse(false, errors);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        if (!(commentRequest.getParentId() == null)) {
            if (commentRepository.findCommentById(commentRequest.getParentId()) == null || postById == null) {
                errors.put("text", "This post and/or comment is absent");
                CommentErrorResponse errorResponse = new CommentErrorResponse(false, errors);
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
            newPostComment = new PostComment(commentRequest.getParentId(), postById, user, LocalDateTime.now(),
                    commentRequest.getText());
        } else {
            newPostComment = new PostComment(postById, user, LocalDateTime.now(), commentRequest.getText());
        }

        PostComment addedComment = commentRepository.save(newPostComment);
        CommentAddResponse newComment = new CommentAddResponse(addedComment.getId());

        return ResponseEntity.ok(newComment);
    }
}
