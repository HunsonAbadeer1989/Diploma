package main.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class EditProfileRequest implements RequestApi{

    private Byte removePhoto;
    private String name;
    private String email;
    private String password;

}
