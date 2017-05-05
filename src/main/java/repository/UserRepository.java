package repository;

import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

}
