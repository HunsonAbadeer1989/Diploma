package main.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "post_comments")
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = "parent_id")
    private long parentId;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    @Column
    private LocalDateTime time;

    @NonNull
    @Column
    private String text;

    public PostComment(@NonNull Post post, @NonNull User user, @NonNull LocalDateTime time, @NonNull String text) {
        this.post = post;
        this.user = user;
        this.time = time;
        this.text = text;
    }
}
