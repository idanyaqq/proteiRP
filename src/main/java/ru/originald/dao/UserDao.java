package ru.originald.dao;

import ru.originald.model.User;

/**
 * Created by Danya on 15/04/2017.
 */
public interface UserDao {

    Long saveUser(User user);
    User getUserInfo(Long id);
    User getUserByUsername(String username);
    boolean getStatus(Long id, boolean status);
}
