package ru.originald.controller;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.UriComponentsBuilder;
import ru.originald.model.User;
import ru.originald.service.UserService;

import java.util.List;

/**
 * Created by Danya on 15/04/2017.
 */

@RestController
@EnableWebMvc
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "users/passport/{passportNumber}", method = RequestMethod.GET)
    public ResponseEntity<User> findUserByPassportNumber(@PathVariable("passportNumber") Long passportNumber) {
        User user = userService.findByPassportNumber(passportNumber);
        return (user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/users/companies/{companyName}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsersByCompany(@PathVariable("companyName") String companyName) {
        List<User> userList = userService.findUserByCompany(companyName);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Void> saveUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        userService.saveUser(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "users/{username}",method = RequestMethod.GET)
    public ResponseEntity<User> findUserByUsername(@PathVariable(value = "username") String userName){
        try {
            User user = userService.findByUsername(userName);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Find User by Id without deep info
    @RequestMapping(value = "/users/UDI/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserWithoutDeepInfo(@PathVariable("id") Long id) throws Exception {
        try {
            User user = userService.findByIdWithoutDeepInfo(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "users/UFI/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserWithFullInfo(@PathVariable("id")Long id){
        try {
            User user = userService.findByIdWithFullInfo(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/users/UWC/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserWithCompanyInfo(@PathVariable("id") Long id) throws Exception {
        try {
            User user = userService.findByIdWithCompany(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> getStatus(@PathVariable("id") Long id, @RequestParam("status") boolean status, UriComponentsBuilder ucBuilder) {
        try {
            User currentUser = userService.findByIdWithoutDeepInfo(id);
            boolean currentStatus = currentUser.getStatus();
            userService.getStatus(id, status);
            boolean updatedStatus = userService.findByIdWithoutDeepInfo(id).getStatus();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setUpgrade("previousStatus=" + (currentStatus ? "online" : "offline")
                    + ",CurrentStatus=" + (updatedStatus ? "online" : "offline"));

            return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/users/changePassword", method = RequestMethod.POST)
    public ResponseEntity<Void> changePassword(@RequestParam("currentPassword") String currentPassword,
                                               @RequestParam("newPassword") String newPassword) {
        try {
            userService.changeOwnPassword(currentPassword, newPassword);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/admin/findAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> findAll() {
        List<User> userList = userService.findAll();
        if (userList != null && !userList.isEmpty()) {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/users/edit/{id}", method = RequestMethod.POST)

    public ResponseEntity<Void> editUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.editUserById(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
