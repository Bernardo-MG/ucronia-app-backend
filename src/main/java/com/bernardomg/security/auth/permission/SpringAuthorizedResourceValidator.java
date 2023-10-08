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

package com.bernardomg.security.auth.permission;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bernardomg.security.auth.springframework.userdetails.ResourceActionGrantedAuthority;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SpringAuthorizedResourceValidator implements AuthorizedResourceValidator {

    public SpringAuthorizedResourceValidator() {
        super();
    }

    @Override
    public final boolean isAuthorized(final String resource, final String action) {
        final Authentication authentication;
        final boolean        authorized;

        // TODO: Check before casting
        authentication = SecurityContextHolder.getContext()
            .getAuthentication();
        if (authentication == null) {
            log.debug("Missing authentication object");
            authorized = false;
        } else if (authentication.isAuthenticated()) {
            authorized = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> isValid(a, resource, action));
            log.debug("Authorized user {} against resource {} with action {}: {}", authentication.getName(), resource,
                action, authorized);
        } else {
            log.debug("User {} is not authenticated", authentication.getName());
            authorized = false;
        }

        return authorized;
    }

    private final boolean isValid(final GrantedAuthority authority, final String resource, final String action) {
        final boolean valid;

        if (authority instanceof final ResourceActionGrantedAuthority resourceAuthority) {
            valid = resourceAuthority.getResource()
                .equals(resource)
                    && resourceAuthority.getAction()
                        .equals(action);
        } else {
            valid = false;
        }

        return valid;
    }

}
