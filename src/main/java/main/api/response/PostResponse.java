package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.model.Post;
import main.model.PostAuthor;
import main.model.PostComment;
import main.model.TagToPost;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse implements ResponseApi {

    private long id;
    private String timestamp;
    private boolean active;
    private PostAuthor user;
    private String title;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private List<Comment> commentsList;
    private Set<String> tagsList;


    public PostResponse(Post post) {
        id = post.getId();
        timestamp = getTimeToString(post.getPublicationTime());
        user = new PostAuthor(post.getUser().getId(), post.getUser().getName());
        title = post.getTitle();
        text = post.getPostText();
        likeCount = (int) post.getVotes().stream().filter(v -> v.getValue() == 1).count();
        dislikeCount = (int) post.getVotes().stream().filter(v -> v.getValue() == -1).count();
        viewCount = post.getViewCount();
        commentsList = new LinkedList<>();
        for (PostComment c : post.getComments()) {
            long commentAuthorId = c.getUser().getId();
            String commentAuthorName = c.getUser().getName();
            String commentAuthorPhoto = c.getUser().getPhoto();

            long commentId = c.getId();
            String commentTimestamp = getTimeToString(c.getTime());
            String commentText = c.getText();

            Comment comment = new Comment(commentId, commentTimestamp, commentText,
                    new Comment.CommentAuthor(commentAuthorId,
                            commentAuthorName,
                            commentAuthorPhoto));
            commentsList.add(comment);
        }
        for(TagToPost t : post.getTagsToPost()){
            String tagName = t.getTag().getName();
            tagsList.add(tagName);
        }
    }

    private String getTimeToString(LocalDateTime publicationTime) {
        return publicationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm"));
    }

    @Data
    @AllArgsConstructor
    static class Comment {

        private long id;
        private String timestamp;
        private String text;
        private CommentAuthor user;

        @Data
        @AllArgsConstructor
        static class CommentAuthor {

            private long id;
            private String name;
            private String photo;

        }
    }

}
