package main.api.request;

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

    public EditProfileRequest(String photo, String name, String email, String password, Byte removePhoto) {
        this.photo = photo;
        this.removePhoto = removePhoto;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Byte getRemovePhoto() {
        return removePhoto;
    }

    public void setRemovePhoto(Byte removePhoto) {
        this.removePhoto = removePhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
