package ru.originald.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.originald.model.Enums.Role;
import ru.originald.model.User;
import ru.originald.repository.UserRepository;

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

        return userRepository.saveAndFlush(user);
    }
    @Transactional
    public User findByIdWithoutDeepInfo(Long id) {
        return userRepository.findByIdWithoutDeepInfo(id);
    }

    @Override
    public User findByIdWithCompany(Long id) {
        return userRepository.findByIdWithoutDeepInfo(id);
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
    public User findByPassportNumber(long passportNumber) {
        return userRepository.findByPassportNumber(passportNumber);
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
