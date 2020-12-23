package main.service.impl;

import main.api.request.AddPostRequest;
import main.api.request.ModerationOfPostRequest;
import main.api.request.VotesRequest;
import main.api.response.*;
import main.model.Post;
import main.repository.PostRepository;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public ResponseEntity<ResponseApi> getPostById(long id) {
        Post post = postRepository.findById(id);
        if (post == null) {
            return new ResponseEntity<>(new NotFoundOrBadRequestResponse("Document not found"), HttpStatus.NOT_FOUND);
        }
        ResponseApi responseApi = new PostResponse(post);
        return new ResponseEntity<>(responseApi, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseApi> getPostsByTag(int offset, int limit, String tag) {
        if (offset < 0 || limit < 1 || tag == null || tag.isBlank() || tag.equals("")) {
            return new ResponseEntity<>(new NotFoundOrBadRequestResponse("Bad request with " + offset + " offset and "
                    + limit + " limit param"),
                    HttpStatus.BAD_REQUEST);
        }
        List<Post> postsByTag = postRepository.getPostsByTag(offset, limit, tag);
        int count = postsByTag.size();
        ResponseApi responseApi = new PostListResponse(count, (ArrayList<Post>) postsByTag);
        return new ResponseEntity<>(responseApi, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseApi> getPostsWithParams(int offset, int limit, String mode) {
        ResponseApi listOfPosts;
        switch(mode){
            case("popular"):
                listOfPosts = new PostListResponse(postRepository.getPopularPosts(offset, limit).size(),
                        postRepository.getPopularPosts(offset, limit));
                break;
            case("best"):
                listOfPosts = new PostListResponse(postRepository.getBestPosts(offset, limit).size(),
                        postRepository.getBestPosts(offset, limit));
                break;
            case("early"):
                listOfPosts = new PostListResponse(postRepository.getEarlyPosts(offset, limit).size(),
                        postRepository.getEarlyPosts(offset, limit));
                break;
            default:
                listOfPosts = new PostListResponse(postRepository.getRecentPosts(offset, limit).size(),
                        postRepository.getRecentPosts(offset, limit));
                break;
        }
        return new ResponseEntity<>(listOfPosts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseApi> getPostsByQuery(int offset, int limit, String query) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> getPostsByDate(int offset, int limit, LocalDate date) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> getPostsForModeration(int offset, int limit, String status) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> getMyPosts(int offset, int limit, String status) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> addPost(AddPostRequest addPostRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> updatePost(AddPostRequest addPostRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> likePost(VotesRequest votesRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> dislikePost(VotesRequest votesRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> moderationOfPost(ModerationOfPostRequest moderationOfPostRequest,
                                                        HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> calendarOfPosts(Integer findYear) {
        int year = findYear == null ? LocalDateTime.now().getYear() : findYear;
        List<Post> yearPosts = postRepository.calendarOfPosts(year);
        HashMap<Date, Integer> postsCountByDate = new HashMap<>();
        for (Post p : yearPosts) {
            Date postDate = Date.valueOf(p.getPublicationTime().toLocalDate());
            Integer postCount = postsCountByDate.getOrDefault(postDate, 0);
            postsCountByDate.put(postDate, postCount + 1);
        }
        List<Integer> allYears = postRepository.getYearsWithAnyPosts();
        return new ResponseEntity<>(new PostsCalendarResponse(allYears, postsCountByDate), HttpStatus.OK);
    }


}
