package main.repository;

import main.model.PostComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<PostComment, Integer> {

    @Query(value = "SELECT * FROM post_comments AS pc " +
            "WHERE pc.id = :id ", nativeQuery = true)
    PostComment findCommentById(@Param("id") long parent_id);

}
