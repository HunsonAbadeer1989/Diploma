package main.service.impl;

import main.api.response.ImageUploadBadResponse;
import main.api.response.ResponseApi;
import main.service.ImageService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${spring.servlet.multipart.max-file-size}")
    private int MAX_FILE_SIZE;

    @Override
    public ResponseEntity<ResponseApi> uploadImage(MultipartFile image, String path) throws Exception {

        ImageUploadBadResponse uploadResponse = new ImageUploadBadResponse();

        boolean checkImage = chekImage(image);

        if (!checkImage) {
            return new ResponseEntity<>(uploadResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            if (image.getSize() < MAX_FILE_SIZE && image.getOriginalFilename() != null) {

                String[] uuidPath = UUID.randomUUID().toString().split("\\-");

                String resultPath = path + "/" + uuidPath[0] + "/" + uuidPath[1] + "/" + uuidPath[2];

                Path uploadDir = Paths.get(resultPath);

                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                Path imagePath = uploadDir.resolve(image.getOriginalFilename());

                Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

                return new ResponseEntity(imagePath.toString(), HttpStatus.OK);

            } else {
                uploadResponse = new ImageUploadBadResponse();
                uploadResponse.setResult(false);

                HashMap<String, String> errors = new HashMap<>(1);
                errors.put("image", "Size of image more than 5Mb");

                uploadResponse.setErrors(errors);
                return new ResponseEntity<>(uploadResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (
                IOException exception) {
            uploadResponse = new ImageUploadBadResponse();
            uploadResponse.setResult(false);

            HashMap<String, String> errors = new HashMap<>(1);
            errors.put("image", "Download image error");

            uploadResponse.setErrors(errors);
            return new ResponseEntity<>(uploadResponse, HttpStatus.OK);
        }
    }

    @Override
    public String uploadUserPhoto(MultipartFile image, String imagePath) throws Exception {

        boolean chekImage = chekImage(image);

        if (!chekImage) {
            return null;
        }

        Path uploadDir = Paths.get(imagePath);

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path uploadPath = uploadDir.resolve(image.getOriginalFilename());
        File resizeFile = new File(String.valueOf(uploadPath));
        simpleResizeImage(image, resizeFile);

        Files.copy(image.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

        return uploadPath.toString();
    }

    private boolean chekImage(MultipartFile image) {

        if (image == null ||
                image.getOriginalFilename() == null ||
                image.getOriginalFilename().isEmpty()) {
            return false;
        }

        String resultFilename = image.getOriginalFilename();

        return resultFilename.split("\\.")[1].equalsIgnoreCase("jpg") ||
                resultFilename.split("\\.")[1].equalsIgnoreCase("png");
    }


    public void simpleResizeImage(MultipartFile photo, File resizeFile) throws Exception {
        Thumbnails.of(photo.getInputStream())
                .crop(Positions.CENTER_LEFT)
                .size(36, 36)
                .keepAspectRatio(true)
                .toFile(resizeFile);
    }

}
