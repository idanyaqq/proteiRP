package ru.originald.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.originald.model.User;

import java.util.List;

/**
 * Created by redin on 5/5/17.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select u from User u LEFT JOIN FETCH u.company")
    List<User> findAll();

    User findByUsername(@Param("username") String username);

    @Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.company WHERE u.id=:id")
    User findByIdWithoutDeepInfo(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET status=:status WHERE id=:id",nativeQuery = true)
    void updateStatus(@Param("id") Long id, @Param("status") boolean status);

    @Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.company WHERE u.company.name =:company_name")
//    @Query(value = "SELECT u FROM User u LEFT JOIN Company c ON u.company.id =c.id WHERE c.name =: company_name")
    List<User> findUsersByCompany(@Param("company_name")String company_name);

    @Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.company WHERE u.passport.number =:passport_number")
    User findByPassportNumber(@Param("passport_number") Long passportNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET username=?1, email=?2, phone=?3,status=?4 WHERE id = ?5",nativeQuery = true)
    void editUserById(String username, String email, long phone,boolean status,long id);

}
