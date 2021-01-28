package main.service.impl;

import main.api.request.AddPostRequest;
import main.api.request.ModerationOfPostRequest;
import main.api.request.VotesRequest;
import main.api.response.*;
import main.model.Post;
import main.model.User;
import main.repository.PostRepository;
import main.repository.UserRepository;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

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
        return createResponse(findByMode(mode, pageable));
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
        if (query != null) {
            pagePost = postRepository.getPostsByQuery(query, page);
            return createResponse(pagePost);
        } else {
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
    public ResponseEntity<ResponseApi> getPostsForModeration(int offset, int limit, String status, Principal principal) {
        Pageable pageable = PageRequest.of(offset, limit);

        Page<Post> pageForModerationResponse;

        if (status.equals("new")) {
            pageForModerationResponse = postRepository.getPostsForModeration(status, pageable);
        } else {
            User user = userRepository.findByEmail(principal.getName());
            pageForModerationResponse = postRepository.getPostsByMyModeration(status, user.getId(), pageable);
        }

        return createResponse(pageForModerationResponse);
    }

    @Override
    public ResponseEntity<ResponseApi> getMyPosts(int offset, int limit, String status, Principal principal) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Post> pageMyPostsResponse;

        if ("inactive".equals(status)) {
            pageMyPostsResponse = postRepository.getMyInactivePosts(principal.getName(), pageable);
        } else {
            pageMyPostsResponse = postRepository.getMyActivePosts(status, principal.getName(), pageable);
        }
        return createResponse(pageMyPostsResponse);
    }

    @Override
    public ResponseEntity<ResponseApi> addPost(AddPostRequest addPostRequest,
                                               Principal principal) {
        LocalDateTime datePost = setDateToPost(addPostRequest.getTimestamp());

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

    private ResponseEntity<ResponseApi> createResponse(Page<Post> page) {
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

    private LocalDateTime setDateToPost(long timestamp) {
        LocalDateTime dateOfPost =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                        TimeZone.getDefault().toZoneId());
        if (dateOfPost.isBefore(LocalDateTime.now())){
            dateOfPost = LocalDateTime.now();
        }
        return dateOfPost;
    }
}
