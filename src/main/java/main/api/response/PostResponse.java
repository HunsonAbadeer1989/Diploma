package main.api.response;

import main.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostResponse implements ResponseApi {

    private int count;
    private List<Post> posts;

    public PostResponse(int count, ArrayList<Post> postsOnSite){
        this.count = count;
        for(Post p : postsOnSite) {
            posts.add(p);
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
