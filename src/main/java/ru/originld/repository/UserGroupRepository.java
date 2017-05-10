package ru.originld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.originld.model.UserGroup;

import java.util.List;

/**
 * Created by redin on 5/10/17.
 */
public interface UserGroupRepository extends JpaRepository<UserGroup,Long> {

    List<UserGroup> findById(long id);

    List<UserGroup> findByName(String name);

    @Query(value = "SELECT * FROM users_to_users_group inner join users_group on group_id = id where user_id =?1", nativeQuery = true)
    List<UserGroup> findByUserId( long userId);


}
