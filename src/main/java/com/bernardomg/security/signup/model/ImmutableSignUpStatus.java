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

package com.bernardomg.security.signup.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Immutable implementation of {@link SignUpStatus}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Data
public class ImmutableSignUpStatus implements SignUpStatus {

    /**
     * Email of the user who attempted to sign up.
     */
    private final String  email;

    /**
     * Flag telling if the sign up was successful.
     */
    private final Boolean successful;

    /**
     * Username of the user who attempted to sign up.
     */
    private final String  username;

    /**
     * Builds a sign up status with the specified arguments.
     *
     * @param user
     *            username
     * @param mail
     *            email
     * @param flag
     *            sign up status
     */
    public ImmutableSignUpStatus(@NonNull final String user, @NonNull final String mail, @NonNull final Boolean flag) {
        super();

        username = user;
        email = mail;
        successful = flag;
    }

}
