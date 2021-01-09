package main.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileRequest implements RequestApi {

    private String photo;
    private Byte removePhoto;
    private String name;
    private String email;
    private String password;

    public EditProfileRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public EditProfileRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public EditProfileRequest(String name, String email, Byte removePhoto, String photo) {
        this.photo = photo;
        this.removePhoto = removePhoto;
        this.name = name;
        this.email = email;
    }

}
