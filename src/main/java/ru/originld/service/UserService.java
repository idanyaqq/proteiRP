package ru.originld.service;

import ru.originld.model.User;

import java.util.List;

/**
 * Created by Danya on 15/04/2017.
 */
public interface UserService {
    List<User> findAll();
    User saveUser(User user);
    User findByIdWA(Long id);
    User findByIdAA2(Long id);
    User findByUsername(String username);
    void getStatus(Long id, boolean status);
    List<User> findUserByCompany(String companyName);
    User findByPassportNUmber(long passportNubmer);
    void changeOwnPassword(String currentPassword,String newPassword);
    void editUserById(User user,long id);
}
