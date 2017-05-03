package ru.originald.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.originald.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by Danya on 15/04/2017.
 */
@Repository
public class UserDaoImpl  implements UserDao{

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Integer saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        return user.getId();
    }

    public User getUserInfo(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (User) session.load(User.class,id);
    }

    public boolean getStatus(int id, boolean status) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class,id);
        if(user!=null) {
            user.setStatus(status);

            return user.getStatus();
        }
        return user.getStatus();
    }
}
