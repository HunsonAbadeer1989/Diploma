package main.service.impl;

import com.sun.istack.NotNull;
import main.api.request.AddPostRequest;
import main.api.request.ModerationOfPostRequest;
import main.api.request.VotesRequest;
import main.api.response.*;
import main.model.Post;
import main.model.PostVotes;
import main.model.User;
import main.repository.PostRepository;
import main.repository.PostVotesRepository;
import main.repository.UserRepository;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final PostVotesRepository postVotesRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PostVotesRepository postVotesRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postVotesRepository = postVotesRepository;
    }

    @Override
    public ResponseEntity<ResponseApi> getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow();
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
        return createMyPostsResponse(findByMode(mode, pageable));
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
            return createMyPostsResponse(pagePost);
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

        User user = userRepository.findByEmail(principal.getName());

        if (!(user.getIsModerator() == 1)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        if (status.equals("new")) {
            pageForModerationResponse = postRepository.getPostsForModeration(status, pageable);
        } else {
            pageForModerationResponse = postRepository.getPostsByMyModeration(status, user.getId(), pageable);
        }

        return createMyPostsResponse(pageForModerationResponse);
    }

    @Override
    public ResponseEntity<ResponseApi> getMyPosts(int offset, int limit, String status, Principal principal) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Post> pageMyPostsResponse = null;

        User user = userRepository.findByEmail(principal.getName());

        switch (status) {
            case "inactive":
                pageMyPostsResponse = postRepository.getMyInactivePosts(principal.getName(), pageable);
                break;
            case "pending":
                pageMyPostsResponse = postRepository.getMyActivePosts("NEW", user.getEmail(), pageable);
                break;
            case "declined":
                pageMyPostsResponse = postRepository.getMyActivePosts("DECLINE", user.getEmail(), pageable);
                break;
            case "published":
                pageMyPostsResponse = postRepository.getMyActivePosts("ACCEPTED", user.getEmail(), pageable);
                break;
        }

        if(pageMyPostsResponse == null){
            return ResponseEntity.ok(new PostListResponse(0, new ArrayList<>()));
        }

        return createMyPostsResponse(pageMyPostsResponse);
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
    public ResponseEntity<ResponseApi> votePost(@NotNull VotesRequest votesRequest, Principal principal) {

        if (principal == null) {
            return ResponseEntity.ok(new VoteResponse(false));
        }

        User user = userRepository.findUserByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

        Post postForVote = postRepository.findById(votesRequest.getPostId())
                .orElseThrow(() -> new NoSuchElementException("Post is not found"));

        PostVotes postVotes = postVotesRepository.findVotes(postForVote.getId(), user.getId());

        if (postVotes == null) {
            PostVotes newPostVote = new PostVotes(user, postForVote, LocalDateTime.now(), (byte) 0);
            postVotes = postVotesRepository.save(newPostVote);
        }

        if (votesRequest.isLike()) {
            if (postVotes.getValue() == 0 || postVotes.getValue() == -1) {
                postVotes.setValue(1);
                postVotesRepository.save(postVotes);
                return new ResponseEntity<>(new VoteResponse(true), HttpStatus.OK);
            }
        } else {
            if (postVotes.getValue() == 0 || postVotes.getValue() == 1) {
                postVotes.setValue(-1);
                postVotesRepository.save(postVotes);
                return new ResponseEntity<>(new VoteResponse(true), HttpStatus.OK);
            }
        }

        return ResponseEntity.ok(new VoteResponse(false));
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

    private ResponseEntity<ResponseApi> createMyPostsResponse(Page<Post> page) {
        List<Post> postsList = page.getContent();
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
        if (dateOfPost.isBefore(LocalDateTime.now())) {
            dateOfPost = LocalDateTime.now();
        }
        return dateOfPost;
    }

}
