package ru.originald.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ru.originald.model.User;
import ru.originald.model.UserGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by redin on 5/12/17.
 */
@Component
@Aspect
public class TransactionalAspect {

    @Pointcut("within(ru.originald.service.*)")
    public void servicePoint() {
    }

    @AfterReturning(pointcut = "servicePoint()",
    returning = "result")
    public Object init(JoinPoint pjp,Object result) throws Throwable {


            String methodName = pjp.getSignature().getName();

            if (methodName.contains("WithoutDeep") ||
                    methodName.contains("findByPassportNumber")) {
                User user = (User) result;
                userWithoutDeepInfo(user);
                result = user;
            }
            if (methodName.contains("WithCompany")) {
                User user = (User) result;
                userWithCompany(user);
                result = user;
            }
            if(methodName.contains("findUserByCompany")||
                    methodName.contains("findAll")){
                List<User> users = (List<User>) result;
                usersByCompany(users);
                return users;
            }
            if(methodName.contains("findByGroupId")||
                    methodName.contains("findByGroupName")){
                UserGroup userGroups = (UserGroup) result;
                userGroupsByGroupId(userGroups);
                result = userGroups;
            }
            if(methodName.contains("getAllUsersGroupAndUsers")){
                List<UserGroup> userGroups = (List<UserGroup>) result;
                allGroupsAndUsers(userGroups);
                result = userGroups;
            }

            if(methodName.contains("findByUserId")){
                List<UserGroup> userGroups = (List<UserGroup>) result;
                userGroupsByUserId(userGroups);
                result = userGroups;
            }
        return result;
    }

    private void allGroupsAndUsers(List<UserGroup> userGroups){
        userGroups.forEach(userGroup ->
                userGroup.getUserList().forEach(user -> {
                user.setCompany(null);
                user.setPassport(null);
                user.setUserGroupForUserUsings(null);
        }));
    }

    private void userWithoutDeepInfo(User user) {
        user.setCompany(null);
        user.setUserGroupForUserUsings(null);
        user.setPassport(null);
    }

    private void userWithCompany(User user){
        user.setUserGroupForUserUsings(null);
        user.setPassport(null);
    }

    private void usersByCompany(List<User> users){
        users.forEach((user)->{ user.setCompany(null);
                                user.setUserGroupForUserUsings(null);
                                user.setPassport(null);});
    }

    private void userGroupsByGroupId(UserGroup userGroups){
        userGroups.getUserList().forEach(user -> {
                    user.setCompany(null);
                    user.setPassport(null);
                    user.setUserGroupForUserUsings(null);
                });
    }

    private void userGroupsByUserId(List<UserGroup> userGroups){
        userGroups.forEach(userGroup ->
                userGroup.setUserList(null));
    }
}
