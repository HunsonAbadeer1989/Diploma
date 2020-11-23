package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.model.Post;
import main.model.PostComment;
import main.model.Tag;
import main.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse implements ResponseApi {

    private long id;
    private String timestamp;
    private boolean active;
    private User user;
    private String title;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private List<PostComment> commentsList;
    private List<Tag> tagsList;


    public PostResponse(Post post){
        id = post.getId();
        timestamp = getTimeToString(post.getPublicationTime());
        user = post.getUser();
        title = post.getTitle();
        text = post.getPostText();
        likeCount = (int) post.getVotes().stream().filter(v -> v.getValue() == 1).count();
        dislikeCount = (int) post.getVotes().stream().filter(v -> v.getValue() == -1).count();
        viewCount = post.getViewCount();
        commentsList = post.getComments();
        tagsList = post.getTags();
    }

    private String getTimeToString(LocalDateTime publicationTime){
        return publicationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm"));
    }

}
