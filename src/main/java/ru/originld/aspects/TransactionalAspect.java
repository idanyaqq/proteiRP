package ru.originld.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import ru.originld.model.Company;
import ru.originld.model.Passport;
import ru.originld.model.User;
import ru.originld.model.to.UserGroupForUserUsing;

import java.util.List;

/**
 * Created by redin on 5/12/17.
 */
@Component
@Aspect
public class TransactionalAspect {

    @Pointcut("within(ru.originld.service.*)")
    public void servicePoint(){}

    @Around("servicePoint()")
    public Object init(ProceedingJoinPoint pjp) throws Throwable {

        Object result = null;

        if(pjp.getSignature().getName().contains("WA")) {

            result = pjp.proceed();
            if (result instanceof User) {
                User user = (User) result;
//                Company company = user.getCompany();
//                List<UserGroupForUserUsing> userGroupForUserUsings = user.getUserGroupForUserUsings();
//                Passport passport = user.getPassport();
                if (user.getCompany() != null) {
                    user.setCompany(null);
                }
                if(user.getUserGroupForUserUsings()!=null ||!user.getUserGroupForUserUsings().isEmpty()){
                    user.setUserGroupForUserUsings(null);
                }
                if(user.getPassport()!=null){
                    user.setPassport(null);
                }

                result = user;
            }
        }

        if(pjp.getSignature().getName().contains("AA2")){
            result = pjp.proceed();

            if(result instanceof User){
                User user = (User) result;
                if(user.getUserGroupForUserUsings()!=null ||!user.getUserGroupForUserUsings().isEmpty()){
                    user.setUserGroupForUserUsings(null);
                }
                if(user.getPassport()!=null){
                    user.setPassport(null);
                }
                Hibernate.initialize(user.getCompany());
            }
        }
        return result;
    }
}
