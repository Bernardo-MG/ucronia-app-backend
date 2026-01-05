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

package com.bernardomg.association.fee.usecase.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.exception.MissingUserInSessionException;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * My fees service based on Spring Security.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Service
@Transactional
public final class SpringSecurityMyFeesService implements MyFeesService {

    /**
     * Logger for the class.
     */
    private static final Logger         log = LoggerFactory.getLogger(SpringSecurityMyFeesService.class);

    private final FeeRepository         feeRepository;

    private final UserProfileRepository userProfileRepository;

    public SpringSecurityMyFeesService(final FeeRepository feeRepo, final UserProfileRepository userProfileRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        userProfileRepository = Objects.requireNonNull(userProfileRepo);
    }

    @Override
    public final Page<Fee> getAllForUserInSession(final Pagination pagination, final Sorting sorting) {
        final Page<Fee>         fees;
        final Optional<Profile> profile;
        final String            username;

        log.info("Getting all the fees for the user in session");

        username = getUsername();
        profile = userProfileRepository.findByUsername(username);
        if (profile.isEmpty()) {
            log.warn("User {} has no member assigned", username);
            fees = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        } else {
            fees = feeRepository.findAllForProfile(profile.get()
                .number(), pagination, sorting);
        }

        log.debug("Got all the fees for the user in session {}: {}", username, fees);

        return fees;
    }

    /**
     * Returns the username for the user in session.
     *
     * @return the username for the user in session
     */
    private final String getUsername() {
        final Authentication authentication;
        final UserDetails    userDetails;

        log.info("Getting all the fees for the user in session");

        authentication = SecurityContextHolder.getContext()
            .getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)
                || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new MissingUserInSessionException();
        }

        userDetails = (UserDetails) authentication.getPrincipal();

        return userDetails.getUsername();
    }

}
