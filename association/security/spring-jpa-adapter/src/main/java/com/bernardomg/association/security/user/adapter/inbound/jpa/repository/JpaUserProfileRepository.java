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
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserAssignedProfileEntity;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserProfileEntity;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserProfileEntityMapper;
import com.bernardomg.association.security.user.domain.model.UserProfile;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.security.adapter.inbound.jpa.model.user.UserEntity;

@Transactional
public final class JpaUserProfileRepository implements UserProfileRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                       log = LoggerFactory.getLogger(JpaUserProfileRepository.class);

    private final UserProfileSpringRepository         profileSpringRepository;

    private final UserAssignedProfileSpringRepository userProfileSpringRepository;

    private final AssociationUserSpringRepository     userSpringRepository;

    public JpaUserProfileRepository(final UserAssignedProfileSpringRepository userProfileSpringRepo,
            final AssociationUserSpringRepository userSpringRepo, final UserProfileSpringRepository profileSpringRepo) {
        super();

        userProfileSpringRepository = Objects.requireNonNull(userProfileSpringRepo);
        userSpringRepository = Objects.requireNonNull(userSpringRepo);
        profileSpringRepository = Objects.requireNonNull(profileSpringRepo);
    }

    @Override
    public final UserProfile assignProfile(final String username, final long number) {
        final UserAssignedProfileEntity           userProfile;
        final Optional<UserEntity>                user;
        final Optional<UserProfileEntity>         profile;
        final UserProfile                         result;
        final Optional<UserAssignedProfileEntity> existingUserProfile;

        log.trace("Assigning profile {} to username {}", number, username);

        user = userSpringRepository.findByUsername(username);
        profile = profileSpringRepository.findByNumber(number);
        if ((user.isPresent()) && (profile.isPresent())) {
            existingUserProfile = userProfileSpringRepository.findByUserUsername(username);

            if (existingUserProfile.isPresent()) {
                userProfile = existingUserProfile.get();
            } else {
                userProfile = new UserAssignedProfileEntity();
                userProfile.setUser(user.get());
            }

            userProfile.setProfile(profile.get());

            userProfileSpringRepository.save(userProfile);
            result = UserProfileEntityMapper.toDomain(profile.get());

            log.trace("Assigned profile {} to username {}", number, username);
        } else {
            log.warn("Failed to assign profile {} to username {}", number, username);

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
    public final Optional<UserProfile> findByUsername(final String username) {
        final Optional<UserAssignedProfileEntity> userMember;
        final Optional<UserProfile>               profile;

        log.trace("Finding profile for username {}", username);

        userMember = userProfileSpringRepository.findByUserUsername(username);
        if (userMember.isPresent() && (userMember.get()
            .getProfile() != null)) {
            profile = Optional.of(UserProfileEntityMapper.toDomain(userMember.get()
                .getProfile()));
        } else {
            profile = Optional.empty();
        }

        log.trace("Found profile for username {}: {}", username, profile);

        return profile;
    }

    @Override
    public final Optional<UserProfile> findOne(final Long number) {
        final Optional<UserProfile> profile;

        log.debug("Finding profile with number {}", number);

        profile = profileSpringRepository.findByNumber(number)
            .map(UserProfileEntityMapper::toDomain);

        log.debug("Found profile with number {}: {}", number, profile);

        return profile;
    }

    @Override
    public final UserProfile unassignProfile(final String username) {
        final Optional<UserEntity>                user;
        final Optional<UserAssignedProfileEntity> assignedUserProfile;
        final UserProfile                         result;
        final UserProfileEntity                   profile;

        log.trace("Unassigning profile from username {}", username);

        user = userSpringRepository.findByUsername(username);
        assignedUserProfile = userProfileSpringRepository.findByUserUsername(username);
        if ((user.isPresent()) && (assignedUserProfile.isPresent()) && (assignedUserProfile.get()
            .getProfile() != null)) {
            profile = assignedUserProfile.get()
                .getProfile();

            userProfileSpringRepository.delete(assignedUserProfile.get());
            result = UserProfileEntityMapper.toDomain(profile);

            log.trace("Unassigned profile  username {}", username);
        } else {
            log.warn("Failed to unassign profile from username {}", username);

            result = null;
        }

        return result;
    }

}
