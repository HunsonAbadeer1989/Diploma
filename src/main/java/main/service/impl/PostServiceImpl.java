package main.service.impl;

import main.api.request.AddPostRequest;
import main.api.request.ModerationOfPostRequest;
import main.api.request.VotesRequest;
import main.api.response.*;
import main.model.Post;
import main.repository.PostRepository;
import main.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        ResponseApi responseApi = new PostListResponse(count, postsByTag);
        return new ResponseEntity<>(responseApi, HttpStatus.OK);
    }

    public ResponseEntity<ResponseApi> getPostsWithParams(String mode, Pageable pageable) {
        Page<Post> pagePost = Page.empty();
        if (mode != null) {
            pagePost = findByMode(mode, pageable);
            return createResponse(pagePost);
        }
        else{
            return getAllPosts(postRepository.getAllPosts(pageable));
        }
    }

    private Page<Post> findByMode(String mode, Pageable pageable) {
        Page<Post> list = Page.empty();
        switch (mode) {
            case "best":
                list = postRepository.getBestPosts(pageable);
                break;
            case "recent":
                list = postRepository.getRecentPosts(pageable);
                break;
            case "popular":
                list = postRepository.getPopularPosts(pageable);
                break;
            default:
                list = postRepository.getEarlyPosts(pageable);
                break;
        }
        return list;
    }

    @Override
    public ResponseEntity<ResponseApi> getPostsByQuery(String query, Pageable page) {
        Page<Post> pagePost = Page.empty();
        if(query != null) {
            pagePost = postRepository.getPostsByQuery(query, page);
            return createResponse(pagePost);
        }
        else{
            return getAllPosts(postRepository.getAllPosts(page));
        }
    }

    @Override
    public ResponseEntity<ResponseApi> getPostsByDate(String date, Pageable page) {
        Page<Post> pagePost = Page.empty();
        if (date != null) {
            pagePost = postRepository.getPostsByDate(date, page);
        }
        PostListResponse response = new PostListResponse((int) pagePost.getTotalElements(), pagePost.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
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

    private ResponseEntity<ResponseApi> createResponse(Page<Post> page){
        List<Post> posts = page.getContent();
        List<Post> postsList = new ArrayList<>(posts);
        ResponseApi listResponse = new PostListResponse((int) page.getTotalElements(), postsList);
        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }

    private ResponseEntity<ResponseApi> getAllPosts(Page<Post> allPosts) {
        List<Post> posts = allPosts.getContent();
        ResponseApi listResponse = new PostListResponse((int) allPosts.getTotalElements(), new ArrayList<>(posts));
        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }


}
