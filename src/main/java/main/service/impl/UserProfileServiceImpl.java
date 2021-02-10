package main.service.impl;

import main.api.request.EditProfileRequest;
import main.api.request.EditProfileWithPhotoRequest;
import main.api.response.EditProfileResponse;
import main.api.response.ImageResponse;
import main.api.response.ResponseApi;
import main.api.response.StatisticResponse;
import main.config.SecurityConfig;
import main.model.Post;
import main.model.User;
import main.repository.PostRepository;
import main.repository.UserRepository;
import main.service.UserProfileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final SecurityConfig securityConfig;

    @Value("${spring.servlet.multipart.location}")
    private String PATH;
    @Value("${spring.servlet.multipart.max-file-size}")
    private int MAX_FILE_SIZE;


    public UserProfileServiceImpl(UserRepository userRepository, PostRepository postRepository, SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.securityConfig = securityConfig;
    }

    @Override
    public ResponseEntity<ResponseApi> editMyProfile(EditProfileRequest editRequest,
                                                     Principal principal) {

        EditProfileResponse editResponse = new EditProfileResponse(true);
        HashMap<String, String> errors = new HashMap<>();
        editResponse.setErrors(errors);

        if (editRequest != null && principal != null) {

            String userEmail = principal.getName();
            String password = editRequest.getPassword();
            String email = editRequest.getEmail();
            String name = editRequest.getName();
            Byte removePhoto = editRequest.getRemovePhoto();

            if (!checkEditRequestArgs(null, name, email, password, principal, editResponse)) {
                return new ResponseEntity<>(editResponse, HttpStatus.OK);
            } else if (password == null && removePhoto == null) { // Edit name and email
                userRepository.editNameEmail(name, email, userEmail);
            }

            if (password != null) {

                PasswordEncoder encoder = securityConfig.passwordEncoder();
                String encodePassword = encoder.encode(password);

                if (removePhoto == null) { // Edit name email and password
                    userRepository.editNameEmailPassword(name, email, encodePassword, userEmail);
                }
                else if (removePhoto == 1) {
                    userRepository.editNameEmailAndPhoto(name, email, "", userEmail);
                }
            }

        } else {
            editResponse.setResult(false);
            editResponse.getErrors().put("request", "Request is empty");
        }

        return new ResponseEntity<>(editResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseApi> editMyProfileWithPhoto(MultipartFile photo,
                                                              String name,
                                                              String email,
                                                              String password,
                                                              Principal principal) {

        EditProfileResponse editResponse = new EditProfileResponse(true);
        HashMap<String, String> errors = new HashMap<>();
        editResponse.setErrors(errors);

        if (principal != null) {
            if (!checkEditRequestArgs(photo, name, email, password, principal, editResponse)) {
                return new ResponseEntity<>(editResponse, HttpStatus.OK);
            }

            String userEmail = principal.getName();

            PasswordEncoder encoder = securityConfig.passwordEncoder();
            String encodePassword = encoder.encode(password);

            String filePath = saveUserPhoto(photo, "usersPhoto");
            userRepository.editPasswordAndPhoto(name, email, encodePassword, userEmail, filePath);

        } else {
            editResponse.setResult(false);
            editResponse.getErrors().put("request", "Request is empty");
        }

        return new ResponseEntity<>(editResponse, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ResponseApi> getMyStatistic(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(principal.getName());

        List<Post> posts = postRepository.getUserPosts(user.getId());
        if (posts.size() == 0) {
            return new ResponseEntity<>(
                    new StatisticResponse(0, 0, 0, 0, 0),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(createMyStatisticResponse(posts), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseApi> getAllStatistic() {
        int postsCount = postRepository.countAllPostsOnSite();
        int likesCount = postRepository.countAllVotes(1);
        int dislikesCount = postRepository.countAllVotes(-1);
        int viewsCount = postRepository.sumAllViews();

        Post minimumDatePost = postRepository.getPostWithMinimumDate();
        long firstPublication = minimumDatePost.getPublicationTime().atZone(ZoneId.systemDefault()).toEpochSecond();

        StatisticResponse statisticResponse = new StatisticResponse(postsCount,
                likesCount,
                dislikesCount,
                viewsCount,
                firstPublication);
        return new ResponseEntity<>(statisticResponse, HttpStatus.OK);
    }

    @Override
    public Object uploadImage(MultipartFile image, String folder) {

        ImageResponse imageResponse = new ImageResponse();

        if (image == null ||
                image.getOriginalFilename() == null ||
                image.getOriginalFilename().isEmpty()) {
            imageResponse.setResult(false);

            HashMap<String, String> errors = new HashMap<>(1);
            errors.put("image", "Download image error");

            imageResponse.setErrors(errors);
            return new ResponseEntity<>(imageResponse, HttpStatus.BAD_REQUEST);
        }

        String resultFilename = image.getOriginalFilename();

        if (!(resultFilename.split("\\.")[1].equalsIgnoreCase("jpg") ||
                resultFilename.split("\\.")[1].equalsIgnoreCase("png"))) {
            imageResponse.setResult(false);

            HashMap<String, String> errors = new HashMap<>(1);
            errors.put("image", "Wrong extension of upload file");

            imageResponse.setErrors(errors);
            return new ResponseEntity<>(imageResponse, HttpStatus.BAD_REQUEST);
        }

        HashMap<String, String> paths = createPathForImage(folder);

        try {
            if (image.getSize() < MAX_FILE_SIZE) {

                File uploadImage = new File(System.getProperty("user.dir") + File.separator +
                        paths.get("uploadPath") + File.separator + resultFilename);

                image.transferTo(uploadImage);

                if (folder.equals("usersPhoto")) { // If user profile photo
                    BufferedImage originalImage = ImageIO.read(uploadImage);
                    OutputStream os = new FileOutputStream(uploadImage.getPath(), false);

                    BufferedImage resizedImage = new BufferedImage(36, 36, 5);
                    Graphics2D g = resizedImage.createGraphics();
                    g.drawImage(originalImage, 0, 0, 36, 36, null);
                    g.dispose();

                    ImageIO.write(resizedImage, "jpg", os);
                }

                return paths.get("pathForResponse") + "/" + resultFilename;

            } else {
                imageResponse = new ImageResponse();
                imageResponse.setResult(false);

                HashMap<String, String> errors = new HashMap<>(1);
                errors.put("image", "Size of image more than 5Mb");

                imageResponse.setErrors(errors);
                return new ResponseEntity<>(imageResponse, HttpStatus.OK);
            }
        } catch (
                IOException exception) {
            imageResponse = new ImageResponse();
            imageResponse.setResult(false);

            HashMap<String, String> errors = new HashMap<>(1);
            errors.put("image", "Download image error");

            imageResponse.setErrors(errors);
            return new ResponseEntity<>(imageResponse, HttpStatus.OK);
        }
    }

    private StatisticResponse createMyStatisticResponse(List<Post> posts) {
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

    private HashMap<String, String> createPathForImage(String nameOfFolder) {
        HashMap<String, String> paths = new HashMap<>();
        String imagesPath = PATH + File.separator + nameOfFolder;
        File uploadCatalog = new File(imagesPath);

        if (!uploadCatalog.exists()) {
            uploadCatalog.mkdirs();
        }

        String uploadPath = imagesPath;
        String tempNameOfFolder = nameOfFolder.equals("") ? "" : File.separator + nameOfFolder;
        StringBuilder pathForResponse = new StringBuilder("/upload" + tempNameOfFolder);
        String newUploadPath;
        for (int i = 0; i < 3; i++) {
            String randomString = getRandomString(2);
            newUploadPath = uploadPath + File.separator + randomString;
            pathForResponse.append("/").append(randomString);
            File uploadDir = new File(newUploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            uploadPath = newUploadPath;
        }
        paths.put("uploadPath", uploadPath);
        paths.put("pathForResponse", pathForResponse.toString());
        return paths;
    }

    private String getRandomString(int number) {
        String numbersAndLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(number);

        for (int j = 0; j < number; j++) {
            int index = (int) (numbersAndLetters.length() * Math.random());
            sb.append(numbersAndLetters.charAt(index));
        }

        return sb.toString();
    }

    public boolean checkEditRequestArgs(MultipartFile photo,
                                        String name,
                                        String email,
                                        String password,
                                        Principal principal,
                                        EditProfileResponse editResponse) {

        if (!(photo == null) && photo.getSize() > MAX_FILE_SIZE) {
            editResponse.setResult(false);
            editResponse.getErrors().put("photo", "Size of profile photo more than 5 Mb");
        }

        if (name.isEmpty() || !(name.matches("\\w+"))) {
            editResponse.setResult(false);
            editResponse.getErrors().put("name", "Incorrect name");
        }

        String emailFromRepo = userRepository.findByEmail(principal.getName()).getEmail();

        if (!(email.equals(principal.getName())) && emailFromRepo.equals(email)) {
            editResponse.setResult(false);
            editResponse.getErrors().put("email", "Email: " + email + " already exist");
        }

        if (password != null && password.length() < 6) {
            editResponse.setResult(false);
            editResponse.getErrors().put("password", "Password shorter than 6 symbols");
        }

        return editResponse.isResult();
    }

    protected String saveUserPhoto(MultipartFile photo, String folder) {
        Object pathToPhoto = uploadImage(photo, folder);
        if (pathToPhoto.getClass().getName().equals("java.lang.String")) {
            return (String) pathToPhoto;
        } else {
            return null;
        }
    }

}
