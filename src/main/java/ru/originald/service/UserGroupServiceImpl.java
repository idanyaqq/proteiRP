package ru.originald.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.originald.model.UserGroup;
import ru.originald.repository.UserGroupRepository;

import java.util.List;

/**
 * Created by redin on 5/10/17.
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {
    @Autowired
    private UserGroupRepository userGroupRepository;

    @Override
    public UserGroup findByGroupId(long id) {
        return userGroupRepository.findById(id);
    }

    @Override
    public UserGroup findByGroupName(String name) {
        return userGroupRepository.findByName(name);
    }

    @Override
    public List<UserGroup> findByUserId(long userId) {
        return userGroupRepository.findByUserId(userId);
    }

    @Override
    public List<UserGroup> getAllUsersGroupAndUsers() {
        return userGroupRepository.getAllGroupsAndUsers();
    }
}
