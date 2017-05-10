package ru.originld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.originld.model.UserGroup;
import ru.originld.repository.UserGroupRepository;

import java.util.List;

/**
 * Created by redin on 5/10/17.
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {
    @Autowired
    private UserGroupRepository userGroupRepository;

    @Override
    public List<UserGroup> findByUserGroupId(long id) {
        return userGroupRepository.findById(id);
    }

    @Override
    public List<UserGroup> findByName(String name) {
        return userGroupRepository.findByName(name);
    }

    @Override
    public List<UserGroup> findByUserId(long userId) {
        return userGroupRepository.findByUserId(userId);
    }
}
