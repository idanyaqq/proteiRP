package ru.originald.service;

import ru.originald.model.User;

import java.util.List;

/**
 * Created by Danya on 15/04/2017.
 */
public interface UserService {
    List<User> findAll();
    User saveUser(User user);
    User findByIdWithoutDeepInfo(Long id);
    User findByIdWithCompany(Long id);
    User findByIdWithFullInfo(Long id);
    User findByUsername(String username);
    void getStatus(Long id, boolean status);
    List<User> findUserByCompany(String companyName);
    User findByPassportNumber(long passportNumber);
    void changeOwnPassword(String currentPassword,String newPassword);
    void editUserById(User user,long id);
}
