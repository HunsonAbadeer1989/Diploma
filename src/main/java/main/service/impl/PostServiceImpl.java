package main.service.impl;

import main.api.request.AddPostRequest;
import main.api.request.ModerationOfPostRequest;
import main.api.request.VotesRequest;
import main.api.response.PostListResponse;
import main.api.response.PostResponse;
import main.api.response.PostsCalendarResponse;
import main.api.response.ResponseApi;
import main.model.Post;
import main.repository.PostRepository;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public ResponseEntity<ResponseApi> getPostsWithParams(int offset, int limit, String mode) {
        return null;
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
    public ResponseEntity<ResponseApi> getPostsByTag(int offset, int limit, String tag) {
        List<Post> postsByTag = postRepository.getPostsByTag(tag, limit, offset);
        int count = postsByTag.size();
        ResponseApi responseApi = new PostListResponse(count, (ArrayList<Post>) postsByTag);
        return new ResponseEntity<ResponseApi>(responseApi, HttpStatus.OK);
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
    public ResponseEntity<ResponseApi> getPostById(long id) {
        Post post = postRepository.getPostById(id);
        ResponseApi responseApi = new PostResponse(post);
        return new ResponseEntity<ResponseApi>(responseApi, HttpStatus.OK);
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
    public ResponseEntity<ResponseApi> moderationOfPost(ModerationOfPostRequest moderationOfPostRequest, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> calendarOfPosts(Integer findYear) {
        int year = findYear == null ? LocalDateTime.now().getYear() : findYear;
        List<Post> yearPosts =  postRepository.calenderOfPosts(year);
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
