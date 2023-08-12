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

package com.bernardomg.security.signup.service;

import com.bernardomg.security.email.sender.SecurityMessageSender;
import com.bernardomg.security.signup.model.SignUp;
import com.bernardomg.security.signup.model.SignUpStatus;
import com.bernardomg.security.user.persistence.repository.UserRepository;

import lombok.NonNull;

public final class MailSignUpService implements SignUpService {

    private final SecurityMessageSender mailSender;

    private final SignUpService         wrapped;

    public MailSignUpService(@NonNull final UserRepository repo, @NonNull final SecurityMessageSender mSender) {
        super();

        wrapped = new DefaultSignUpService(repo);

        mailSender = mSender;
    }

    @Override
    public final SignUpStatus signUp(final SignUp signUp) {
        final SignUpStatus status;

        status = wrapped.signUp(signUp);

        if (status.getSuccessful()) {
            // Sends success email
            // TODO: Test this
            // TODO: Can be chained after the sign up call. Maybe use an aspect
            mailSender.sendSignUpMessage(signUp.getUsername(), signUp.getEmail());
        }

        return status;
    }

}
