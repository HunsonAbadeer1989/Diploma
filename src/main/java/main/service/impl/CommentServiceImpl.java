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

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ResponseApi> postComment(CommentRequest commentRequest, Principal principal) {

        User user = userRepository.findUserByEmail(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("user not found"));

        HashMap<String, String> errors = new HashMap<>();

        if (commentRequest.getText().length() < 1) {
            errors.put("text", "Text of comment is too short");
            CommentErrorResponse errorResponse = new CommentErrorResponse(false, errors);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Post post = postRepository.findById(commentRequest.getPostId()).orElseThrow();

        PostComment newPostComment;

        if (!(commentRequest.getParentId() == null)) {
            if (commentRepository.findCommentById(commentRequest.getParentId()) == null || post == null) {
                errors.put("text", "This post and/or comment is absent");
                CommentErrorResponse errorResponse = new CommentErrorResponse(false, errors);
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
            newPostComment = new PostComment(commentRequest.getParentId(), post, user, LocalDateTime.now(),
                    commentRequest.getText());
            return ResponseEntity.ok(new CommentAddResponse(commentRepository.save(newPostComment).getId()));
        }
        newPostComment = new PostComment(post, user, LocalDateTime.now(), commentRequest.getText());
        return ResponseEntity.ok(new CommentAddResponse(commentRepository.save(newPostComment).getId()));

    }
}
