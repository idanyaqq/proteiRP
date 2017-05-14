package ru.originald.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.originald.model.User;

/**
 * Created by redin on 5/12/17.
 */
@Component
@Aspect
public class TransactionalAspect {

    @Pointcut("within(ru.originald.service.*)")
    public void servicePoint() {
    }

    @Around("servicePoint()")
    public Object init(ProceedingJoinPoint pjp) throws Throwable {

        Object result = pjp.proceed();

        if (result instanceof User) {

            User user = (User) result;

            if (pjp.getSignature().getName().contains("WithoutDeep")) {
                userWithoutDeepInfo(user);
            }
            if (pjp.getSignature().getName().contains("WithCompany")){
                userWithCompany(user);
            }
            result = user;

        }
        return result;
    }

    private void userWithoutDeepInfo(User user) {

        user.setCompany(null);

        user.setUserGroupForUserUsings(null);

        user.setPassport(null);

//        return user;
    }

    private void userWithCompany(User user){

        user.setUserGroupForUserUsings(null);

        user.setPassport(null);

//        return user;
    }
}
