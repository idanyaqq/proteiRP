package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dao.UserDao;
import model.User;

/**
 * Created by Danya on 15/04/2017.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public Integer saveUser(User user) {
        return userDao.saveUser(user);
    }

    @Transactional
    public User getUserInfo(int id) {
        return userDao.getUserInfo(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Transactional
    public boolean getStatus(int id, boolean status) {
        return userDao.getStatus(id, status);
    }
}