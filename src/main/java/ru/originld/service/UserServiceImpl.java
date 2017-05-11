package ru.originld.service;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import ru.originld.model.Enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.originld.model.User;
import ru.originld.repository.UserRepository;

import java.util.List;

/**
 * Created by Danya on 15/04/2017.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCrypt;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcUserDetailsManager jdbcUserDetailsManager;


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


    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public void getStatus(Long id, boolean status) {
         userRepository.updateStatus(id, status);
    }

    @Override
    public List<User> findUserByCompany(String companyName) {
        return userRepository.findUsersByCompany(companyName);
    }

    @Override
    public User findByPassportNUmber(long passportNubmer) {
        return userRepository.findByPassportNumber(passportNubmer);
    }

    @Override
    public void changeOwnPassword(String currentPassword, String newPassword) {
        try {
            String encodedPass = preparePassword(newPassword);
            jdbcUserDetailsManager.changePassword(currentPassword,encodedPass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String preparePassword(String newPassword) throws Exception{
        if(newPassword!=null && newPassword.length()>= User.MIN_PASS_LENGTH){
            return bCrypt.encode(newPassword);
        }
        else{
           throw new Exception("Password is empty or length less then 6.");
        }
    }
}
