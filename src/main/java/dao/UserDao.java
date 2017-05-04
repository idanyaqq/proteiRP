package dao;

import model.User;

/**
 * Created by Danya on 15/04/2017.
 */
public interface UserDao {

    Integer saveUser(User user);
    User getUserInfo(int id);
    User getUserByUsername(String username);
    boolean getStatus(int id, boolean status);
}
