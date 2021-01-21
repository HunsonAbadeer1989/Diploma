package main.repository;

import main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    @Query(value = "SELECT * FROM users u" +
            " WHERE u.code = ?", nativeQuery = true)
    User getUserByCode(String code);

    @Query(value = "SELECT * FROM users u" +
            " WHERE u.name = ?", nativeQuery = true)
    User getUserByName(String name);

}
