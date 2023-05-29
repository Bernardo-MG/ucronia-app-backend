/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.security.login.service;

import java.util.function.Predicate;

import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.request.LoginRequest;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultLoginService implements LoginService {

    private final Predicate<LoginRequest> isValid;

    private final LoginStatusProvider     loginStatusProvider;

    public DefaultLoginService(@NonNull final LoginStatusProvider loginStatusProv,
            @NonNull final Predicate<LoginRequest> valid) {
        super();

        loginStatusProvider = loginStatusProv;
        isValid = valid;
    }

    @Override
    public final LoginStatus login(final LoginRequest login) {
        final Boolean valid;
        final String  username;

        username = login.getUsername()
            .toLowerCase();

        log.debug("Log in attempt for {}", username);

        valid = isValid.test(login);

        return loginStatusProvider.getStatus(username, valid);
    }

}
