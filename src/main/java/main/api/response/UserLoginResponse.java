package main.api.response;


import lombok.Data;
import main.repository.PostRepository;

@Data
public class UserLoginResponse {
    private long id;
    private String name;
    private String photo;
    private String email;
    private boolean moderation;
    private int moderationCount;
    private boolean settings;

    public void setModerationCount(String email, PostRepository postRepository) {
        this.moderationCount = postRepository.findAllPostsIsModerate(email);
    }

}
