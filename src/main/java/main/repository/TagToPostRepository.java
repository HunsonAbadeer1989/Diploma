package main.repository;

import main.model.TagToPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TagToPostRepository extends JpaRepository<TagToPost, Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT IGNORE INTO tag2post (post_id, tag_id) VALUES (:post_id, :tag_id)")
    void insertTag2Post(@Param("post_id") long post_id, @Param("tag_id") long tag_id);

}
