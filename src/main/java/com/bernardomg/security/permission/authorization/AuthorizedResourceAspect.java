
package com.bernardomg.security.permission.authorization;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public final class AuthorizedResourceAspect {

    private final AuthorizedResourceValidator authValidator;

    public AuthorizedResourceAspect(final AuthorizedResourceValidator validator) {
        super();

        authValidator = validator;
    }

    @Before("@annotation(com.bernardomg.security.permission.authorization.AuthorizedResource)")
    public final void before(final ProceedingJoinPoint call) {
        final MethodSignature    signature;
        final Method             method;
        final AuthorizedResource myAnnotation;
        final boolean            authorized;

        signature = (MethodSignature) call.getSignature();
        method = signature.getMethod();

        myAnnotation = method.getAnnotation(AuthorizedResource.class);
        authorized = authValidator.isAuthorized(myAnnotation.resource(), myAnnotation.action());

        if (!authorized) {
            // TODO: Use a better exception
            throw new RuntimeException();
        }
    }

}
