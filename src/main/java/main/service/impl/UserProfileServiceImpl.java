package main.service.impl;

import main.api.request.EditProfileRequest;
import main.api.response.ResponseApi;
import main.api.response.StatisticResponse;
import main.model.Post;
import main.model.User;
import main.repository.PostRepository;
import main.repository.UserRepository;
import main.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.ZoneId;
import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public UserProfileServiceImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public ResponseEntity<ResponseApi> editMyProfile(EditProfileRequest editProfileRequest,
                                                     Principal principal) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> getMyStatistic(Principal principal) {
        if(principal == null ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(principal.getName());

        List<Post> posts = postRepository.getUserPosts(user.getId());
        if(posts.size() == 0){
            return new ResponseEntity<>(
                    new StatisticResponse(0,0,0,0,0),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(createStatisticResponse(posts), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseApi> getAllStatistic() {
        List<Post> posts = postRepository.getAllPostsOnSite();
        return new ResponseEntity<>(createStatisticResponse(posts), HttpStatus.OK);
    }

    private StatisticResponse createStatisticResponse(List<Post> posts){
        int postsCount = posts.size();
        int likesCount = 0;
        int dislikesCount = 0;
        int viewsCount = 0;
        long firstPublication = 0L;
        for (Post post : posts) {
            likesCount += post.getVotes().stream().filter(vote -> vote.getValue() == 1).count();
            dislikesCount += post.getVotes().stream().filter(vote -> vote.getValue() == -1).count();
            viewsCount = viewsCount + post.getViewCount();
            if (firstPublication == 0L) {
                firstPublication = post.getPublicationTime().atZone(ZoneId.systemDefault()).toEpochSecond();
            } else {
                long dateOfPublication = post.getPublicationTime().atZone(ZoneId.systemDefault()).toEpochSecond();
                if (firstPublication > dateOfPublication) {
                    firstPublication = dateOfPublication;
                }
            }
        }
        return new StatisticResponse(postsCount, likesCount, dislikesCount, viewsCount, firstPublication);
    }
}
