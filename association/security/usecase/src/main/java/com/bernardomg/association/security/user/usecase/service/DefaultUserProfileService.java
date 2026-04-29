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

package com.bernardomg.association.security.user.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.security.account.domain.exception.MissingAccountProfileException;
import com.bernardomg.association.security.account.domain.model.ProfileAccount.Profile;
import com.bernardomg.association.security.account.domain.repository.AccountProfileRepository;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.usecase.domain.UserProfile;
import com.bernardomg.association.security.user.usecase.validation.UserProfileNameNotEmptyRule;
import com.bernardomg.security.user.domain.exception.MissingUsernameException;
import com.bernardomg.security.user.domain.model.User;
import com.bernardomg.security.user.domain.repository.UserRepository;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import jakarta.transaction.Transactional;

@Transactional
public final class DefaultUserProfileService implements UserProfileService {

    /**
     * Logger for the class.
     */
    private static final Logger            log = LoggerFactory.getLogger(DefaultUserProfileService.class);

    private final AccountProfileRepository accountProfileRepository;

    private final Validator<UserProfile>   assignProfileValidator;

    private final UserProfileRepository    userProfileRepository;

    private final UserRepository           userRepository;

    public DefaultUserProfileService(final UserRepository userRepo, final AccountProfileRepository accountProfileRepo,
            final UserProfileRepository userProfileRepo) {
        super();

        userRepository = Objects.requireNonNull(userRepo);
        accountProfileRepository = Objects.requireNonNull(accountProfileRepo);
        userProfileRepository = Objects.requireNonNull(userProfileRepo);

        assignProfileValidator = new FieldRuleValidator<>(new UserProfileNameNotEmptyRule(userProfileRepository));
    }

    @Override
    public final Profile assignProfile(final String username, final long profile) {
        final User        readUser;
        final Profile     readProfile;
        final UserProfile userProfile;

        log.debug("Assigning profile {} to {}", profile, username);

        readUser = userRepository.findOne(username)
            .orElseThrow(() -> {
                log.error("Missing user {}", username);
                throw new MissingUsernameException(username);
            });

        readProfile = accountProfileRepository.findOne(profile)
            .orElseThrow(() -> {
                log.error("Missing account profile {}", profile);
                throw new MissingAccountProfileException(profile);
            });

        userProfile = new UserProfile(profile, username);
        assignProfileValidator.validate(userProfile);

        userProfileRepository.assignProfile(readUser.username(), readProfile.number());

        return readProfile;
    }

    @Override
    public final Optional<Profile> getProfile(final String username) {
        final Optional<Profile> profile;

        log.trace("Reading profile for {}", username);

        if (!userRepository.exists(username)) {
            log.error("Missing user {}", username);
            throw new MissingUsernameException(username);
        }

        profile = userProfileRepository.findByUsername(username);

        log.trace("Read profile for {}: {}", username, profile);

        return profile;
    }

    @Override
    public final Profile unassignProfile(final String username) {
        final boolean exists;
        final Profile profile;

        log.trace("Unassigning profile to {}", username);

        exists = userRepository.exists(username);
        if (!exists) {
            log.error("Missing user {}", username);
            throw new MissingUsernameException(username);
        }

        profile = userProfileRepository.unassignProfile(username);

        log.trace("Unassigned profile to {}", username);

        return profile;
    }

}
