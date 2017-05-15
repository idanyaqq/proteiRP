package ru.originald.service;

import ru.originald.model.UserGroup;

import java.util.List;

/**
 * Created by redin on 5/10/17.
 */
public interface UserGroupService {

    UserGroup findByGroupId(long id);
    UserGroup findByGroupName(String name);
    List<UserGroup> findByUserId(long userId);
    List<UserGroup> getAllUsersGroupAndUsers();
}
