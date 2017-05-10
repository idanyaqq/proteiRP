package ru.originld.service;

import ru.originld.model.UserGroup;

import java.util.List;

/**
 * Created by redin on 5/10/17.
 */
public interface UserGroupService {

    List<UserGroup> findByUserGroupId(long id);
    List<UserGroup> findByName(String name);
    List<UserGroup> findByUserId(long userId);
}
