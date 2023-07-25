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

package com.bernardomg.security.password.recovery.service;

import com.bernardomg.security.password.recovery.model.PasswordRecoveryStatus;

/**
 * Password recovery service. Handles the steps requires to change a password.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface PasswordRecoveryService {

    public PasswordRecoveryStatus changePassword(final String token, final String newPassword);

    /**
     * Starts the password recovery for a user, identified by the mail.
     *
     * @param email
     *            email for recovering the user
     * @return the recovery attempt status
     */
    public PasswordRecoveryStatus startPasswordRecovery(final String email);

    /**
     * Validate a password recovery token.
     *
     * @param token
     *            token to validate
     * @return the status indicating if the token is valid or not
     */
    public PasswordRecoveryStatus validateToken(final String token);

}
