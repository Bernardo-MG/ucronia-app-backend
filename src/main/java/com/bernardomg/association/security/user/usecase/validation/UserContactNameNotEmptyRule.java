/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

package com.bernardomg.association.security.user.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.security.user.domain.model.UserContact;
import com.bernardomg.association.security.user.domain.repository.UserContactRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the user person has a name.
 */
public final class UserContactNameNotEmptyRule implements FieldRule<UserContact> {

    /**
     * Logger for the class.
     */
    private static final Logger         log = LoggerFactory.getLogger(UserContactNameNotEmptyRule.class);

    private final UserContactRepository userContactRepository;

    public UserContactNameNotEmptyRule(final UserContactRepository userContactRepo) {
        super();

        userContactRepository = Objects.requireNonNull(userContactRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final UserContact contact) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (userContactRepository.existsByContactForAnotherUser(contact.username(), contact.number())) {
            log.error("Contact {} already assigned to a user", contact.number());
            fieldFailure = new FieldFailure("existing", "contact", contact.number());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
