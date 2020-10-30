package main.model;

import lombok.Data;

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

    @OneToMany(mappedBy = "user")
    private List<PostComment> comments;
    @OneToMany(mappedBy = "user")
    private List<PostVotes> postVotes;
    @OneToMany(mappedBy = "moderatorId")
    private List<Post> postsModerated = new ArrayList<>();

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
