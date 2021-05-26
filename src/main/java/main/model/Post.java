package main.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "tagsToPost")
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "is_active")
    @NonNull
    private int isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status")
    @NonNull
    private ModerationStatus moderationStatus;

    @Column(name = "moderator_id", nullable = true)
    private Long moderatorId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;

    @Column(name = "publication_time")
    @NonNull
    private LocalDateTime publicationTime;

    @Column
    @NonNull
    private String title;

    @Column(name = "text")
    @NonNull
    private String postText;

    @Column(name = "view_count")
    private int viewCount;

    @OneToMany(mappedBy = "post")
    private List<PostComment> comments;

    @ManyToMany(mappedBy = "posts", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Tag> tagsToPost;

    @OneToMany(mappedBy = "post")
    private List<PostVotes> votes;


    public Post(@NonNull int isActive,
                @NonNull User user,
                @NonNull LocalDateTime publicationTime,
                @NonNull String title,
                @NonNull String postText) {
        this.isActive = isActive;
        this.user = user;
        this.publicationTime = publicationTime;
        this.title = title;
        this.postText = postText;
    }
}