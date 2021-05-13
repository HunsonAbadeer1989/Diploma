package main.repository;

import main.model.Post;
import main.model.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    @Query(value = "SELECT * FROM posts AS p " +
            "WHERE p.is_active=1 " +
            "AND p.publication_time < NOW() " +
            "AND p.moderation_status = 'ACCEPTED' ", nativeQuery = true)
        // All POSTS
    Page<Post> getAllPosts(Pageable page);

    @Query(value = "SELECT * FROM posts AS p " +
            "WHERE p.is_active=1 " +
            "AND p.publication_time < NOW() " +
            "AND p.moderation_status = 'ACCEPTED' " +
            "ORDER BY p.publication_time DESC ", nativeQuery = true)
        // RECENT POSTS
    Page<Post> getRecentPosts(Pageable page);

    @Query(value = "SELECT * FROM posts AS p " +
            "LEFT JOIN (SELECT post_id, SUM(value) AS sum_values " +
            "FROM post_votes GROUP BY post_id) " +
            "AS sum_votes ON p.id=sum_votes.post_id WHERE p.is_active=1 " +
            "AND p.publication_time < NOW() " +
            "AND p.moderation_status = 'ACCEPTED' " +
            "ORDER BY sum_values DESC, p.view_count DESC ", nativeQuery = true)
        // BEST POSTS
    Page<Post> getBestPosts(Pageable page);

    @Query(value = "SELECT * FROM posts AS p " +
            "WHERE p.is_active=1 " +
            "AND p.publication_time < NOW() " +
            "AND p.moderation_status = 'ACCEPTED' " +
            "ORDER BY (SELECT COUNT(*) FROM post_comments " +
            "WHERE post_id=p.id) DESC ", nativeQuery = true)
        // POPULAR POSTS BY COMMENTS
    Page<Post> getPopularPosts(Pageable page);

    @Query(value = "SELECT * FROM posts AS p " +
            "WHERE p.is_active=1 " +
            "AND p.publication_time < NOW() " +
            "AND p.moderation_status = 'ACCEPTED' " +
            "ORDER BY p.publication_time ASC", nativeQuery = true)
        // EARLY POSTS
    Page<Post> getEarlyPosts(Pageable page);

    Optional<Post> findById(long id);

    @Query(value = "SELECT COUNT(id) AS count FROM posts", nativeQuery = true)
        // COUNT ALL POSTS
    int countAllPostsOnSite();

    @Query(value = "SELECT * FROM posts AS p " +
            "WHERE DATEDIFF(p.publication_time, ?1) = 0 AND p.is_active = 1 " +
            "ORDER BY p.publication_time DESC", nativeQuery = true)
        // SEARCH BY DATE
    Page<Post> getPostsByDate(String date, Pageable page);

    @Query(value = "SELECT * FROM posts AS p " +
            "WHERE p.moderation_status = 'ACCEPTED' ", nativeQuery = true)
    List<Post> getAllPostsOnSite();

    @Query(value = "SELECT * FROM posts AS p " +
            "LEFT JOIN (SELECT post_id, text AS comment FROM post_comments GROUP BY post_id)" +
            "AS comments ON p.id=comments.post_id WHERE p.is_active=1 ORDER BY comments DESC ", nativeQuery = true)
        // GET ALL COMMENTS
    List<PostComment> getAllComments();

    @Query(value = "SELECT DISTINCT * FROM posts AS p " +
            "INNER JOIN tag2post AS t2p ON p.id=t2p.post_id " +
            "INNER JOIN tags AS t ON t.id=t2p.tag_id " +
            "WHERE (t.name LIKE %?3%) " +
            "AND p.is_active = 1 " +
            "AND p.moderation_status = 'ACCEPTED' " +
            "AND p.publication_time < NOW() " +
            "ORDER BY p.publication_time DESC " +
            "LIMIT ?2 OFFSET ?1 ", nativeQuery = true)
        // GET POSTS BY TAG
    List<Post> getPostsByTag(int offset, int limit, String tag);

    @Query(value = "SELECT * FROM posts AS p WHERE YEAR(p.publication_time) = ? ", nativeQuery = true)
    List<Post> calendarOfPosts(Integer year);

    @Query(value = "SELECT DISTINCT YEAR(p.publication_time) AS post_year " +
            "FROM posts p ORDER BY post_year DESC", nativeQuery = true)
    List<Integer> getYearsWithAnyPosts();

    @Query(value = "SELECT COUNT(p.id) FROM posts p " +
            "WHERE p.is_active = 1 " +
            "AND p.moderation_status = 'NEW'", nativeQuery = true)
    int countPostsForModeration();

    @Query(value = "SELECT * FROM posts AS p " +
            "WHERE p.publication_time < NOW() " +
            "AND p.is_active = 1 " +
            "AND p.moderation_status = :status " +
            "ORDER BY p.publication_time DESC", nativeQuery = true)
    Page<Post> getPostsForModeration(@Param("status") String status, Pageable pageable);

    @Query(value = "SELECT * FROM posts AS p " +
            "WHERE p.moderator_id = :id " +
            "AND p.is_active = 1 " +
            "AND p.moderation_status = :status " +
            "AND p.publication_time < NOW() " +
            "ORDER BY p.publication_time DESC", nativeQuery = true)
    Page<Post> getPostsByMyModeration(@Param("status") String status, @Param("id") long id, Pageable pageable);


    @Query(value = "SELECT * FROM posts AS p " +
            "WHERE p.publication_time < NOW() " +
            "AND p.is_active = 1 " +
            "AND p.moderation_status = 'ACCEPTED' " +
            "AND (p.text LIKE %:query% OR title LIKE %:query%)", nativeQuery = true)
    Page<Post> getPostsByQuery(@Param("query") String query, Pageable page);

    @Query(value = "SELECT p.* FROM posts p " +
            "JOIN users u ON u.id = p.user_id " +
            "WHERE u.email = :email " +
            "AND p.is_active = 0 " +
            "AND p.publication_time < NOW() " +
            "ORDER BY p.publication_time DESC", nativeQuery = true)
    Page<Post> getMyInactivePosts(@Param("email") String email, Pageable pageable);

    @Query(value = "SELECT p.* FROM posts p " +
            "JOIN users u ON u.id = p.user_id " +
            "WHERE u.email = :email " +
            "AND p.is_active = 1 " +
            "AND p.moderation_status = :status " +
            "AND p.publication_time < NOW() " +
            "ORDER BY p.publication_time DESC", nativeQuery = true)
    Page<Post> getMyActivePosts(@Param("status") String status, @Param("email") String email, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM posts p " +
            "JOIN users u ON u.id = p.user_id " +
            "WHERE u.email = :email " +
            "AND p.is_active = 1 " +
            "AND p.moderation_status = 'NEW'", nativeQuery = true)
    int findAllPostsIsModerate(@Param("email") String email);

    @Query(value = "SELECT * FROM posts AS p " +
            "WHERE p.user_id = :user_id " +
            "AND p.is_active = 1 " +
            "AND p.moderation_status = 'ACCEPTED' ", nativeQuery = true)
    List<Post> getUserPosts(@Param("user_id") Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE posts p " +
            "SET p.is_active = :is_active, " +
            "p.moderation_status = 'NEW', " +
            "p.publication_time = :publication_time, " +
            "p.title = :title, " +
            "p.text = :text " +
            "WHERE p.id = :post_id ", nativeQuery = true)
    void updatePostByUser(@Param("post_id") long id,
                          @Param("is_active") int isActive,
                          @Param("publication_time") LocalDateTime publicationTime,
                          @Param("title") String title,
                          @Param("text") String postText);

    @Modifying
    @Transactional
    @Query(value = "UPDATE posts p " +
            "SET p.is_active = :is_active, " +
            "p.publication_time = :publication_time, " +
            "p.title = :title, " +
            "p.text = :text " +
            "WHERE p.id = :post_id ", nativeQuery = true)
    void updatePostByModerator(@Param("post_id") long id,
                          @Param("is_active") int isActive,
                          @Param("publication_time") LocalDateTime publicationTime,
                          @Param("title") String title,
                          @Param("text") String postText);

    @Query(value = "SELECT COUNT(*) FROM posts AS p " +
            "JOIN post_votes AS pv " +
            "ON p.id = pv.post_id " +
            "WHERE p.is_active = 1 " +
            "AND p.moderation_status = 'ACCEPTED' " +
            "AND pv.value = :value ", nativeQuery = true)
    int countAllVotes(@Param("value") int value);

    @Query(value = "SELECT SUM(p.view_count) FROM posts AS p " +
            "WHERE p.is_active = 1 " +
            "AND p.moderation_status = 'ACCEPTED' ", nativeQuery = true)
    int sumAllViews();

    @Query(value = "SELECT * FROM posts AS p " +
            "WHERE p.is_active = 1 " +
            "AND p.moderation_status = 'ACCEPTED' " +
            "ORDER BY p.publication_time ASC LIMIT 1 ", nativeQuery = true)
    Post getPostWithMinimumDate();

    @Modifying
    @Transactional
    @Query(value = "UPDATE posts AS p " +
            "SET p.moderator_id = :moderator_id, " +
            "p.moderation_status = :status " +
            "WHERE p.id = :post_id ", nativeQuery = true)
    void updateModeratorField(@Param("post_id")Post postById,
                              @Param("moderator_id") long id,
                              @Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE posts AS p " +
            "SET p.view_count = :view_count " +
            "WHERE p.id = :post_id ", nativeQuery = true)
    void updatePostViewCount(@Param("post_id")Long id, @Param("view_count") int i);
}
