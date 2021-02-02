package main.repository;

import main.model.PostVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PostVotesRepository extends JpaRepository<PostVotes, Long> {

    @Query(value = "SELECT * FROM post_votes AS p " +
            "WHERE p.post_id = :post_id " +
            "AND p.user_id = :user_id ", nativeQuery = true)
    PostVotes findVotes(@Param("post_id") Long postId, @Param("user_id") Long userId);

    @Query(value = "UPDATE post_votes AS pv " +
            "SET pv.value = :value " +
            "WHERE pv.post_id = :post_id " +
            "AND pv.user_id = :user_id ", nativeQuery = true)
    void updateVotes(@Param("post_id") long postId, @Param("user_id") long userId, @Param("value") int value);

    @Query(value = "INSERT INTO post_votes (time, value, post_id, user_id) " +
            "VALUES (:time, :value, :post_id, :user_id )", nativeQuery = true)
    PostVotes createVotes(@Param("time") LocalDateTime time,
                          @Param("value") int value,
                          @Param("post_id") long postId,
                          @Param("user_id") long userId);
}
