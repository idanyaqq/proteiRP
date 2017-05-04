package ru.originald.dao;

import ru.originald.model.User;

/**
 * Created by Danya on 15/04/2017.
 */
public interface UserDao {

    Integer saveUser(User user);
    User getUserInfo(int id);
    boolean getStatus(int id, boolean status);
}
