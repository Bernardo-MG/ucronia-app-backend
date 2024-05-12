/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.fee.usecase.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the user fee service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Slf4j
@Transactional
public final class DefaultUserFeeService implements UserFeeService {

    private final FeeRepository        feeRepository;

    private final UserPersonRepository userMemberRepository;

    public DefaultUserFeeService(final FeeRepository feeRepo, final UserPersonRepository userMemberRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        userMemberRepository = Objects.requireNonNull(userMemberRepo);
    }

    @Override
    public final Iterable<Fee> getAllForUserInSession(final Pageable pageable) {
        final Authentication   authentication;
        final Iterable<Fee>    fees;
        final UserDetails      userDetails;
        final Optional<Person> person;

        log.debug("Getting all the fees for the user in session");

        authentication = SecurityContextHolder.getContext()
            .getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            fees = List.of();
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            userDetails = (UserDetails) authentication.getPrincipal();
            person = userMemberRepository.findByUsername(userDetails.getUsername());
            if (person.isEmpty()) {
                log.warn("User {} has no member assigned", userDetails.getUsername());
                fees = List.of();
            } else {
                fees = feeRepository.findAllForMember(person.get()
                    .getNumber(), pageable);
            }
        } else {
            fees = List.of();
        }

        return fees;
    }

}
