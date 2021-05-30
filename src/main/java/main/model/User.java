package main.model;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "is_moderator")
    private int isModerator;
    @Column(name = "reg_time")
    private LocalDateTime regTime;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String code;
    @Column
    private String photo;
    @Column(name = "code_time")
    private LocalDateTime codeTime;

    @OneToMany(mappedBy = "user")
    private List<PostComment> comments;
    @OneToMany(mappedBy = "user")
    private List<PostVotes> postVotes;
    @OneToMany(mappedBy = "moderatorId")
    private List<Post> postsModerated = new ArrayList<>();

    public Role getRole(){
        return isModerator == 1 ? Role.MODERATOR : Role.USER;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", regTime=" + regTime +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
