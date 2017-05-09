package ru.originld.repository;

import ru.originld.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by redin on 5/5/17.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(@Param("username") String username);

    User findById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users_test SET status=:status WHERE id=:id",nativeQuery = true)
    void updateStatus(@Param("id") Long id, @Param("status") boolean status);

    @Query(value = "SELECT u FROM User u WHERE u.company.name =:company_name")
//    @Query(value = "SELECT u FROM User u LEFT JOIN Company c ON u.company.id =c.id WHERE c.name =: company_name")
    List<User> findUsersByCompany(@Param("company_name")String company_name);

    @Query(value = "SELECT u FROM User u WHERE u.passport.number =:passport_number")
    User findByPassportNumber(@Param("passport_number") Long passportNumber);

}
