package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.model.Post;
import main.model.PostAuthor;
import main.model.PostComment;
import main.model.TagToPost;

import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse implements ResponseApi {

    private long id;
    private long timestamp;
    private boolean active;
    private PostAuthor user;
    private String title;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private List<Comment> comments;
    private Set<String> tagsList;


    public PostResponse(Post post) {
        id = post.getId();
        timestamp = post.getPublicationTime().atZone(ZoneId.systemDefault()).toEpochSecond();
        user = new PostAuthor(post.getUser().getId(), post.getUser().getName());
        title = post.getTitle();
        text = post.getPostText();
        likeCount = (int) post.getVotes().stream().filter(v -> v.getValue() == 1).count();
        dislikeCount = (int) post.getVotes().stream().filter(v -> v.getValue() == -1).count();
        viewCount = post.getViewCount();
        comments = new LinkedList<>();
        for (PostComment pc : post.getComments()) {
            long commentAuthorId = pc.getUser().getId();
            String commentAuthorName = pc.getUser().getName();
            String commentAuthorPhoto = pc.getUser().getPhoto();

            long commentId = pc.getId();
            long commentTimestamp = pc.getTime().atZone(ZoneId.systemDefault()).toEpochSecond();
            String commentText = pc.getText();

            Comment comment = new Comment(commentId, commentTimestamp, commentText,
                    new Comment.CommentAuthor(commentAuthorId,
                            commentAuthorName,
                            commentAuthorPhoto));
            comments.add(comment);
        }
        tagsList = new TreeSet<>();
        for(TagToPost t : post.getTagsToPost()){
            String tagName = t.getTag().getName();
            tagsList.add(tagName);
        }
    }

    @Data
    @AllArgsConstructor
    static class Comment {

        private long id;
        private long timestamp;
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
