package main.repository;

import main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Optional<User> findUserByEmail(String email);

    @Query(value = "SELECT * FROM users u" +
            " WHERE u.code = ?", nativeQuery = true)
    User getUserByCode(String code);

    @Query(value = "SELECT * FROM users u" +
            " WHERE u.name = ?", nativeQuery = true)
    User getUserByName(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET name = :name, " +
            "email = :email " +
            "WHERE email = :userEmail ", nativeQuery = true)
    void editNameEmail(@Param("name") String name,
                       @Param("email") String email,
                       @Param("userEmail") String userEmail);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET name = :name, " +
            "email = :email, " +
            "password = :password " +
            "WHERE email = :userEmail ", nativeQuery = true)
    void editNameEmailPassword(@Param("name") String name,
                               @Param("email") String email,
                               @Param("password") String password,
                               @Param("userEmail") String userEmail);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET " +
            "name = :name, " +
            "email = :email, " +
            "password = :password, " +
            "photo = :filePath " +
            "WHERE email = :userEmail ", nativeQuery = true)
    void editPasswordAndPhoto(@Param("name") String name,
                              @Param("email") String email,
                              @Param("password") String password,
                              @Param("userEmail") String userEmail,
                              @Param("filePath") String filePath);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET " +
            "name = :name, " +
            "email = :email, " +
            "photo = :photo " +
            "WHERE email = :userEmail ", nativeQuery = true)
    void editNameEmailAndPhoto(@Param("name")String name,
                               @Param("email")String email,
                               @Param("photo")String photo,
                               @Param("userEmail")String userEmail);

    @Modifying
    @Transactional
    @Query(value ="UPDATE users SET " +
            "code = :code, " +
            "codeTime = :codeTime " +
            "WHERE email = :email", nativeQuery = true)
    void updateUserCode(@Param("email") String email, @Param("code") String code, @Param("codeTime") String codeTime);

    @Modifying
    @Transactional
    @Query(value="UPDATE users SET " +
            "password = :password " +
            "WHERE code = :code", nativeQuery = true)
    void updatePassword(@Param("code") String code, @Param("password") String encodePassword);

    @Modifying
    @Transactional
    @Query(value="UPDATE users SET " +
            "code = null, " +
            "codeTime = null " +
            "WHERE codeTime < :codeTime ", nativeQuery = true)
    void clearAllCodes(@Param("codeTime") String codeTime);
}
