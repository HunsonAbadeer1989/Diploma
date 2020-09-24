package main.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="is_active")
    private int isActive;

    @Enumerated(EnumType.STRING)
    @Column(name="moderation_status")
    private ModerationStatus moderationStatus;

    @Column(name="moderator_id", nullable = true)
    private Long moderatorId = null;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "publication_time")
    private LocalDateTime publicationTime;

    @Column
    private String title;

    @Column(name = "text")
    private String postText;

    @Column(name="view_count")
    private int viewCount;

    @OneToMany(mappedBy="post")
    private List<PostComment> comments;

    @OneToMany
    @JoinTable(name = "tag2post",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;

    @OneToMany(mappedBy="post")
    private List<PostVotes> votes;

    public List<PostComment> getComments() {
        return comments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public Long getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(Long moderator) {
        this.moderatorId = moderator;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(LocalDateTime publicationTime) {
        this.publicationTime = publicationTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public List<PostVotes> getVotes() {
        return votes;
    }

    public void setVotes(List<PostVotes> votes) {
        this.votes = votes;
    }
}
