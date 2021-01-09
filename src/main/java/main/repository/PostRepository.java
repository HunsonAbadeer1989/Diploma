package main.repository;

import main.model.Post;
import main.model.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    @Query(value = "SELECT * FROM posts AS p WHERE p.is_active=1 " +
            "AND p.publication_time < NOW() " +
            "AND p.moderation_status = 'ACCEPTED' " , nativeQuery = true)
        // All POSTS
    Page<Post> getAllPosts(Pageable page);

    @Query(value = "SELECT * FROM posts AS p WHERE p.is_active=1 " +
            "AND p.publication_time < NOW() " +
            "AND p.moderation_status = 'ACCEPTED' " +
            "ORDER BY p.publication_time DESC " , nativeQuery = true)
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

    Post findById(long id);

    @Query(value = "SELECT COUNT(id) AS count FROM posts", nativeQuery = true)
        // COUNT ALL POSTS
    int countAllPostsOnSite();

    @Query(value = "SELECT * FROM posts AS p " +
            "WHERE DATEDIFF(p.publication_time, ?1) = 0 AND p.is_active = 1 " +
            "ORDER BY p.publication_time DESC", nativeQuery = true)
        // SEARCH BY DATE
    Page<Post> getPostsByDate(String date, Pageable page);


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

    @Query(value ="SELECT * FROM posts AS p " +
            "WHERE p.publication_time < NOW() " +
            "AND p.is_active = 1 " +
            "AND p.moderation_status = 'ACCEPTED' " +
            "AND (p.text LIKE %?1% OR title LIKE %?1%)", nativeQuery = true)
    Page<Post> getPostsByQuery(String query, Pageable page);
}
