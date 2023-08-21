
package com.bernardomg.security.permission.authorization;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public final class AuthorizedResourceAspect {

    private final AuthorizedResourceValidator authValidator;

    public AuthorizedResourceAspect(final AuthorizedResourceValidator validator) {
        super();

        authValidator = validator;
    }

    @Before("@annotation(com.bernardomg.security.permission.authorization.AuthorizedResource)")
    public final void before(final JoinPoint call) {
        final MethodSignature    signature;
        final Method             method;
        final AuthorizedResource annotation;
        final boolean            authorized;

        signature = (MethodSignature) call.getSignature();
        method = signature.getMethod();

        annotation = method.getAnnotation(AuthorizedResource.class);
        authorized = authValidator.isAuthorized(annotation.resource(), annotation.action());

        if (!authorized) {
            log.debug("User is not authorized with action {} for resource {}", annotation.action(),
                annotation.resource());
            // TODO: Use a better exception
            throw new AccessDeniedException("Missing authentication");
        }
    }

}
