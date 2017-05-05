package service;

import model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import model.User;
import repository.UserRepository;

/**
 * Created by Danya on 15/04/2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCrypt;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public User saveUser(User user) {
        String userPassword = user.getPassword();
        user.setPassword(bCrypt.encode(userPassword));
        if(user.getRole() == null){
            user.setRole(Role.ROLE_USER);
        }
        if(!user.getStatus()){
            user.setStatus(true);
        }
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void getStatus(Long id, boolean status) {
         userRepository.updateStatus(id, status);
    }
}
