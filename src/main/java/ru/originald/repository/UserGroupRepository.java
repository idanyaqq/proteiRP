package ru.originald.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.originald.model.UserGroup;

import java.util.List;

/**
 * Created by redin on 5/10/17.
 */
public interface UserGroupRepository extends JpaRepository<UserGroup,Long> {

    @Query(value = "SELECT ug FROM UserGroup ug LEFT JOIN FETCH ug.userList WHERE ug.id=?1")
    UserGroup findById(long id);

    @Query(value = "SELECT ug FROM UserGroup ug LEFT JOIN FETCH ug.userList WHERE ug.name=?1")
    UserGroup findByName(String name);

    @Query(value = "SELECT * FROM users_to_users_group LEFT JOIN users_group on group_id = id where user_id =?1", nativeQuery = true)
    List<UserGroup> findByUserId(long userId);

    @Query(value = "SELECT DISTINCT ug FROM UserGroup ug LEFT JOIN FETCH ug.userList")
    List<UserGroup> getAllGroupsAndUsers();



}
