package main.service;


import main.api.request.CommentRequest;
import main.api.response.ResponseApi;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface CommentService {

    ResponseEntity<ResponseApi> postComment(CommentRequest commentRequest, Principal principal);

}
