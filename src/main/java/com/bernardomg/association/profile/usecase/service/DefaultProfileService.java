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

package com.bernardomg.association.profile.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.profile.domain.exception.MissingContactMethodException;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.domain.filter.ProfileQuery;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.usecase.validation.ProfileIdentifierNotExistForAnotherRule;
import com.bernardomg.association.profile.usecase.validation.ProfileIdentifierNotExistRule;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

/**
 * Default implementation of the contact service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultProfileService implements ProfileService {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(DefaultProfileService.class);

    private final ContactMethodRepository contactMethodRepository;

    private final Validator<Profile>      createProfileValidator;

    private final Validator<Profile>      patchProfileValidator;

    private final ProfileRepository       profileRepository;

    private final Validator<Profile>      updateProfileValidator;

    public DefaultProfileService(final ProfileRepository profileRepo, final ContactMethodRepository contactMethodRepo) {
        super();

        profileRepository = Objects.requireNonNull(profileRepo);
        contactMethodRepository = Objects.requireNonNull(contactMethodRepo);
        createProfileValidator = new FieldRuleValidator<>(new ProfileIdentifierNotExistRule(profileRepo));
        updateProfileValidator = new FieldRuleValidator<>(new ProfileIdentifierNotExistForAnotherRule(profileRepo));
        patchProfileValidator = new FieldRuleValidator<>(new ProfileIdentifierNotExistForAnotherRule(profileRepo));
    }

    @Override
    public final Profile create(final Profile profile) {
        final Profile created;

        log.debug("Creating profile {}", profile);

        // TODO: maybe send an exception with all
        profile.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        createProfileValidator.validate(profile);

        created = profileRepository.save(profile);

        log.debug("Created profile {}", created);

        return created;
    }

    @Override
    public final Profile delete(final long number) {
        final Profile existing;

        log.debug("Deleting profile {}", number);

        existing = profileRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing profile {}", number);
                throw new MissingProfileException(number);
            });

        profileRepository.delete(number);

        log.debug("Deleted profile {}", number);

        return existing;
    }

    @Override
    public final Page<Profile> getAll(final ProfileQuery query, final Pagination pagination, final Sorting sorting) {
        final Page<Profile> read;

        log.debug("Reading profiles with query {}, pagination {} and sorting {}", query, pagination, sorting);

        read = profileRepository.findAll(query, pagination, sorting);

        log.debug("Read profiles with query {}, pagination {} and sorting {}: {}", query, pagination, sorting, read);

        return read;
    }

    @Override
    public final Optional<Profile> getOne(final long number) {
        final Optional<Profile> profile;

        log.debug("Reading profile {}", number);

        profile = profileRepository.findOne(number);
        if (profile.isEmpty()) {
            log.error("Missing profile {}", number);
            throw new MissingProfileException(number);
        }

        log.debug("Read profile {}", profile);

        return profile;
    }

    @Override
    public final Profile patch(final Profile profile) {
        final Profile existing;
        final Profile toSave;
        final Profile saved;

        log.debug("Patching profile {} using data {}", profile.number(), profile);

        // TODO: Apply the creation validations

        existing = profileRepository.findOne(profile.number())
            .orElseThrow(() -> {
                log.error("Missing profile {}", profile.number());
                throw new MissingProfileException(profile.number());
            });

        // TODO: maybe send an exception with all
        profile.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        toSave = copy(existing, profile);

        patchProfileValidator.validate(toSave);

        saved = profileRepository.save(toSave);

        log.debug("Patched profile {}: {}", profile.number(), saved);

        return saved;
    }

    @Override
    public final Profile update(final Profile profile) {
        final Profile saved;

        log.debug("Updating profile {} using data {}", profile.number(), profile);

        // TODO: Identificator must be unique or empty
        // TODO: The membership maybe can't be removed

        if (!profileRepository.exists(profile.number())) {
            log.error("Missing profile {}", profile.number());
            throw new MissingProfileException(profile.number());
        }

        // TODO: maybe send an exception with all
        profile.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        updateProfileValidator.validate(profile);

        saved = profileRepository.save(profile);

        log.debug("Updated profile {}: {}", profile.number(), saved);

        return saved;
    }

    private final void checkContactMethodExists(final ContactMethod contactMethod) {
        if (!contactMethodRepository.exists(contactMethod.number())) {
            log.error("Missing contact method {}", contactMethod.number());
            throw new MissingContactMethodException(contactMethod.number());
        }
    }

    private final Profile copy(final Profile existing, final Profile updated) {
        final ProfileName name;

        if (updated.name() == null) {
            name = existing.name();
        } else {
            name = new ProfileName(Optional.ofNullable(updated.name()
                .firstName())
                .orElse(existing.name()
                    .firstName()),
                Optional.ofNullable(updated.name()
                    .lastName())
                    .orElse(existing.name()
                        .lastName()));
        }
        return new Profile(Optional.ofNullable(updated.identifier())
            .orElse(existing.identifier()),
            Optional.ofNullable(updated.number())
                .orElse(existing.number()),
            name, Optional.ofNullable(updated.birthDate())
                .orElse(existing.birthDate()),
            updated.contactChannels(), Optional.ofNullable(updated.address())
                .orElse(existing.address()),
            Optional.ofNullable(updated.comments())
                .orElse(existing.comments()),
            Optional.ofNullable(updated.types())
                .orElse(existing.types()));
    }

}
