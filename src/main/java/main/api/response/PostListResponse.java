package main.api.response;

import lombok.Data;
import main.model.Post;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostListResponse implements ResponseApi {

    private int count;
    private List<PostResponse> posts;

    public PostListResponse(int count, ArrayList<Post> postsOnSite){
        this.count = count;
        posts = new ArrayList<>();
        for(Post p : postsOnSite) {
            PostResponse postResponse = new PostResponse(p);
            posts.add(postResponse);
        }
    }

}
