/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2023 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.security.auth.access;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;

import lombok.extern.slf4j.Slf4j;

/**
 * Aspect which wraps the code marked by {@code AuthorizedResource} and applies resource-based authentication. The
 * validation is applied against the user in session.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Aspect
@Slf4j
public final class RequireResourceAccessAspect {

    /**
     * Authorization validator. Makes sure the user in session has the required authorities.
     */
    private final ResourceAccessValidator authValidator;

    public RequireResourceAccessAspect(final ResourceAccessValidator validator) {
        super();

        authValidator = validator;
    }

    @Before("@annotation(com.bernardomg.security.auth.access.RequireResourceAccess)")
    public final void before(final JoinPoint call) {
        final MethodSignature       signature;
        final Method                method;
        final RequireResourceAccess annotation;
        final boolean               authorized;

        signature = (MethodSignature) call.getSignature();
        method = signature.getMethod();

        annotation = method.getAnnotation(RequireResourceAccess.class);
        authorized = authValidator.isAuthorized(annotation.resource(), annotation.action());

        if (!authorized) {
            log.debug("User is not authorized with action {} for resource {}", annotation.action(),
                annotation.resource());
            // TODO: Use a better exception
            throw new AccessDeniedException("Missing authentication");
        }
    }

}
