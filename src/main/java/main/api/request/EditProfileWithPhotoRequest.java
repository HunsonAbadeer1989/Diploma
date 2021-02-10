package main.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileWithPhotoRequest implements RequestApi{

    private MultipartFile photo;
    private Byte removePhoto;
    private String name;
    private String email;
    private String password;

}
