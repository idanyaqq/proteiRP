package ru.originld.controller;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.UriComponentsBuilder;
import ru.originld.model.User;
import ru.originld.service.UserService;

import java.util.List;

/**
 * Created by Danya on 15/04/2017.
 */

@RestController
@EnableWebMvc
//@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "users/passport/{passportNumber}",method = RequestMethod.GET)
    public ResponseEntity<User> findUserByPassportNumber(@PathVariable("passportNumber") Long passportNumber){
        User user = userService.findByPassportNUmber(passportNumber);
        return (user!=null?new ResponseEntity<User>(user,HttpStatus.OK):new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/users/companies/{companyName}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsersByCompany(@PathVariable("companyName") String companyName){
        List<User> userList = userService.findUserByCompany(companyName);
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }



    @RequestMapping(value = "/users",method = RequestMethod.POST)
    public ResponseEntity<Void> saveUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        userService.saveUser(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserInfo(@PathVariable("id") Long id) throws Exception {
        try {
            User user = userService.findById(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> getStatus(@PathVariable("id") Long id, @RequestParam("status") boolean status, UriComponentsBuilder ucBuilder) {
        try {
            User currentUser = userService.findById(id);
            boolean currentStatus = currentUser.getStatus();
            userService.getStatus(id, status);
            boolean updatedStatus = userService.findById(id).getStatus();
                    HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(ucBuilder.path("/users/{id}?previousStatus=" + (currentStatus ? "online" : "offline") + "&CurrentStatus=" + (updatedStatus ? "online" : "offline"))
                    .buildAndExpand(currentUser.getId())
                    .toUri());

            return new ResponseEntity<Void>(httpHeaders, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

}
