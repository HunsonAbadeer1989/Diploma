package main.api.response;

import lombok.Data;
import main.model.Post;
import main.model.PostAuthor;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostListResponse implements ResponseApi {

    static final int ANNOUNCE = 100;

    private int count;
    private List<PostForListResponse> posts;

    public PostListResponse(int count, List<Post> postsOnSite) {
        this.count = count;
        posts = new ArrayList<>();
        for (Post p : postsOnSite) {
            PostForListResponse postResponse = new PostForListResponse(p);
            posts.add(postResponse);
        }
    }

    @Data
    static class PostForListResponse {

        private long id;
        private long timestamp;
        private String title;
        private String announce;
        private int likeCount;
        private int dislikeCount;
        private int commentCount;
        private int viewCount;
        private PostAuthor user;

        public PostForListResponse(Post post) {
            this.id = post.getId();
            this.timestamp = post.getPublicationTime().atZone(ZoneId.systemDefault()).toEpochSecond();
            this.title = post.getTitle();
            this.announce = post.getPostText().length() < ANNOUNCE ? post.getPostText()
                    : post.getPostText().substring(0, ANNOUNCE) + "...";
            this.likeCount = (int) post.getVotes().stream().filter(v -> v.getValue() == 1).count();
            this.dislikeCount = (int) post.getVotes().stream().filter(v -> v.getValue() == -1).count();
            this.commentCount = post.getComments().size();
            this.viewCount = post.getViewCount();
            this.user = new PostAuthor(post.getUser().getId(), post.getUser().getName());

        }

    }

}
