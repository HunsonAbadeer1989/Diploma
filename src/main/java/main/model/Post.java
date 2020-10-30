package main.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "is_active")
    private int isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status")
    private ModerationStatus moderationStatus;

    @Column(name = "moderator_id", nullable = true)
    private Long moderatorId = null;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "publication_time")
    private LocalDateTime publicationTime;

    @Column
    private String title;

    @Column(name = "text")
    private String postText;

    @Column(name = "view_count")
    private int viewCount;

    @OneToMany(mappedBy = "post")
    private List<PostComment> comments;

    @OneToMany
    @JoinTable(name = "tag2post",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;

    @OneToMany(mappedBy = "post")
    private List<PostVotes> votes;

}