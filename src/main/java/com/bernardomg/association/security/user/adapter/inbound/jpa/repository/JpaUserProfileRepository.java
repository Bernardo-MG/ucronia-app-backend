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

package com.bernardomg.association.security.user.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactEntityMapper;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserProfileEntity;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.security.user.adapter.inbound.jpa.model.UserEntity;
import com.bernardomg.security.user.adapter.inbound.jpa.repository.UserSpringRepository;

@Repository
@Transactional
public final class JpaUserProfileRepository implements UserProfileRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaUserProfileRepository.class);

    private final ProfileSpringRepository     profileSpringRepository;

    private final UserProfileSpringRepository userProfileSpringRepository;

    private final UserSpringRepository        userSpringRepository;

    public JpaUserProfileRepository(final UserProfileSpringRepository userProfileSpringRepo,
            final UserSpringRepository userSpringRepo, final ProfileSpringRepository profileSpringRepo) {
        super();

        userProfileSpringRepository = Objects.requireNonNull(userProfileSpringRepo);
        userSpringRepository = Objects.requireNonNull(userSpringRepo);
        profileSpringRepository = Objects.requireNonNull(profileSpringRepo);
    }

    @Override
    public final Profile assignProfile(final String username, final long number) {
        final UserProfileEntity       userProfile;
        final Optional<UserEntity>    user;
        final Optional<ProfileEntity> contact;
        final Profile                 result;

        log.trace("Assigning contact {} to username {}", number, username);

        user = userSpringRepository.findByUsername(username);
        contact = profileSpringRepository.findByNumber(number);
        if ((user.isPresent()) && (contact.isPresent())) {
            userProfile = new UserProfileEntity();
            userProfile.setUserId(user.get()
                .getId());
            userProfile.setProfile(contact.get());
            userProfile.setUser(user.get());

            userProfileSpringRepository.save(userProfile);
            result = ContactEntityMapper.toDomain(contact.get());

            log.trace("Assigned contact {} to username {}", number, username);
        } else {
            log.warn("Failed to assign contact {} to username {}", number, username);

            result = null;
        }

        return result;
    }

    @Override
    public final boolean existsByProfileForAnotherUser(final String username, final long number) {
        final boolean exists;

        log.trace("Checking if username {} exists for a user with a number distinct from {}", username, number);

        exists = userProfileSpringRepository.existsByNotUsernameAndMemberNumber(username, number);

        log.trace("Username {} exists for a user with a number distinct from {}: {}", username, number, exists);

        return exists;
    }

    @Override
    public final Optional<Profile> findByUsername(final String username) {
        final Optional<UserEntity>        user;
        final Optional<UserProfileEntity> userMember;
        final Optional<Profile>           contact;

        log.trace("Finding contact for username {}", username);

        user = userSpringRepository.findByUsername(username);
        if (user.isPresent()) {
            // TODO: Simplify this, use JPA relationships
            userMember = userProfileSpringRepository.findByUserId(user.get()
                .getId());
            if ((userMember.isPresent()) && (userMember.get()
                .getProfile() != null)) {
                contact = Optional.of(ContactEntityMapper.toDomain(userMember.get()
                    .getProfile()));
            } else {
                contact = Optional.empty();
            }
        } else {
            contact = Optional.empty();
        }

        log.trace("Found contact for username {}: {}", username, contact);

        return contact;
    }

    @Override
    public final Profile unassignProfile(final String username) {
        final Optional<UserEntity> user;
        final Profile              contact;

        log.debug("Deleting user {}", username);

        user = userSpringRepository.findByUsername(username);
        if (user.isPresent()) {
            contact = findByUsername(username).orElse(null);

            // TODO: handle relationships
            // TODO: why not delete by username?
            userProfileSpringRepository.deleteByUserId(user.get()
                .getId());

            log.debug("Deleted user {}", username);
        } else {
            contact = null;
        }

        return contact;
    }

}
