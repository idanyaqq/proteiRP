package ru.originld.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.originld.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by Danya on 15/04/2017.
 */
@Repository
public class UserDaoImpl  implements UserDao{
    @Autowired
    private SessionFactory sessionFactory;

//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    @Override
    public Long saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        return user.getId();
    }

    @Override
    public User getUserInfo(Long id) {
        Session session = sessionFactory.getCurrentSession();

        return (User) session.load(User.class,id);
    }

    @Override
    public User getUserByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        return (User)session.load(User.class,username);
    }

    @Override
    public boolean getStatus(Long id, boolean status) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class,id);
        if(user!=null) {
            user.setStatus(status);

            return user.getStatus();
        }
        return user.getStatus();
    }
}
