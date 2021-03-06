package ru.originald.controller;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.originald.model.UserGroup;
import ru.originald.service.UserGroupService;

import java.util.List;

/**
 * Created by redin on 5/10/17.
 */
@Controller
@EnableWebMvc
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @RequestMapping(value = "users/group/fbgi/{userGroupId}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserGroup> findByUserGroupId(@PathVariable("userGroupId")long id){
        try {
            UserGroup userGroupsList = userGroupService.findByGroupId(id);
            return new ResponseEntity<>(userGroupsList, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "users/group/fbgn/{groupName}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserGroup> findByGroupName(@PathVariable("groupName") String groupName){
        try {
            UserGroup userGroupsList = userGroupService.findByGroupName(groupName);
            return new ResponseEntity<>(userGroupsList, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "users/group/fbui/{userId}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserGroup>> findByUserId(@PathVariable(value = "userId") long userId){
        try {
            List<UserGroup> userGroupsList = userGroupService.findByUserId(userId);
            return new ResponseEntity<>(userGroupsList, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/admin/group/getAll",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserGroup>> getAllUsersGroupAndUsers(){
        List<UserGroup> listOfAll = userGroupService.getAllUsersGroupAndUsers();
        if(listOfAll!=null){
            return new ResponseEntity<>(listOfAll,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
