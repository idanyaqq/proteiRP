package ru.originld.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.originld.model.Enums.Role;
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


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user) {
        String userPassword = user.getPassword();
        Role userRole = user.getRole();
        boolean status = user.getStatus();

        user.setPassword((userPassword==null||userPassword.isEmpty()
                        ?bCrypt.encode("user")                //UUID.randomUUID().toString()
                        :bCrypt.encode(userPassword)));

        user.setRole(userRole==null?Role.ROLE_USER:userRole);

        user.setStatus(status? true :true);

//        if(userPassword == null || userPassword.isEmpty()){
//            String generatedPassword = UUID.randomUUID().toString();
//            user.setPassword(bCrypt.encode(generatedPassword));
//        } else {
//            user.setPassword(bCrypt.encode(userPassword));
//        }

//        if(user.getRole() == null){
//            user.setRole(Role.ROLE_USER);
//        }
//        if(!user.getStatus()){
//            user.setStatus(true);
//        }
        return userRepository.saveAndFlush(user);
    }
    @Transactional
    public User findByIdWA(Long id) {
        User user = userRepository.findByIdAW(id);
//        Hibernate.initialize(user.getCompany());
        return user;
    }

    @Override
    public User findByIdAA2(Long id) {
        return userRepository.findByIdAW(id);
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

    @Override
    public void editUserById(User user,long id) {
        userRepository.editUserById(user.getUsername(),user.getEmail(),user.getPhone(),user.getStatus(),id);
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
