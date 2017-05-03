package ru.originald.controller;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.UriComponentsBuilder;
import ru.originald.model.User;
import ru.originald.service.UserService;

/**
 * Created by Danya on 15/04/2017.
 */

@RestController
@EnableWebMvc
@RequestMapping("/users")
public class UserController {

    @Qualifier("usersService")
    @Autowired
    private UserService userService;



    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        userService.saveUser(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserInfo(@PathVariable("id") int id) throws Exception {
        try {
            User user = userService.getUserInfo(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> getStatus(@PathVariable("id") Integer id, @RequestParam("status") boolean status, UriComponentsBuilder ucBuilder) {
        try {
            User currentUser = userService.getUserInfo(id);
            boolean currentStatus = currentUser.getStatus();
            boolean updatedStatus = userService.getStatus(id, status);
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
