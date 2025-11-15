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

package com.bernardomg.association.member.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.domain.exception.MissingContactMethodException;
import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.domain.repository.ContactMethodRepository;
import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.filter.MemberQuery;
import com.bernardomg.association.member.domain.model.MemberContact;
import com.bernardomg.association.member.domain.repository.MemberContactRepository;
import com.bernardomg.association.member.usecase.validation.MemberContactIdentifierNotExistForAnotherRule;
import com.bernardomg.association.member.usecase.validation.MemberContactIdentifierNotExistRule;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

/**
 * Default implementation of the member contact service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultMemberContactService implements MemberContactService {

    /**
     * Logger for the class.
     */
    private static final Logger            log = LoggerFactory.getLogger(DefaultMemberContactService.class);

    private final ContactMethodRepository  contactMethodRepository;

    private final ContactRepository        contactRepository;

    private final Validator<MemberContact> createMemberValidator;

    private final MemberContactRepository  memberContactRepository;

    private final Validator<MemberContact> patchMemberValidator;

    private final Validator<MemberContact> updateMemberValidator;

    public DefaultMemberContactService(final ContactRepository contactRepo,
            final MemberContactRepository memberContactRepo, final ContactMethodRepository contactMethodRepo) {
        super();

        contactRepository = Objects.requireNonNull(contactRepo);
        memberContactRepository = Objects.requireNonNull(memberContactRepo);
        contactMethodRepository = Objects.requireNonNull(contactMethodRepo);
        createMemberValidator = new FieldRuleValidator<>(new MemberContactIdentifierNotExistRule(contactRepository));
        updateMemberValidator = new FieldRuleValidator<>(
            new MemberContactIdentifierNotExistForAnotherRule(contactRepository));
        patchMemberValidator = new FieldRuleValidator<>(
            new MemberContactIdentifierNotExistForAnotherRule(contactRepository));
    }

    @Override
    public final MemberContact create(final MemberContact member) {
        final MemberContact toCreate;
        final MemberContact created;
        final Long          number;

        log.debug("Creating member {}", member);

        // TODO: maybe send an exception with all
        member.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        // Set number
        number = contactRepository.findNextNumber();

        toCreate = new MemberContact(member.identifier(), number, member.name(), member.birthDate(), member.active(),
            member.renew(), member.contactChannels());

        createMemberValidator.validate(toCreate);

        created = memberContactRepository.save(toCreate);

        log.debug("Created member {}", created);

        return created;
    }

    @Override
    public final MemberContact delete(final long number) {
        final MemberContact existing;

        log.debug("Deleting member {}", number);

        existing = memberContactRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing member {}", number);
                throw new MissingMemberException(number);
            });

        contactRepository.delete(number);

        log.debug("Deleted member {}", number);

        return existing;
    }

    @Override
    public final Page<MemberContact> getAll(final MemberQuery filter, final Pagination pagination,
            final Sorting sorting) {
        final Page<MemberContact> read;

        log.debug("Reading members with query {}, pagination {} and sorting {}", filter, pagination, sorting);

        read = memberContactRepository.findAll(filter, pagination, sorting);

        log.debug("Read members with query {}, pagination {} and sorting {}: {}", filter, pagination, sorting, read);

        return read;
    }

    @Override
    public final Optional<MemberContact> getOne(final long number) {
        final Optional<MemberContact> member;

        log.debug("Reading member {}", number);

        member = memberContactRepository.findOne(number);
        if (member.isEmpty()) {
            log.error("Missing member {}", number);
            throw new MissingMemberException(number);
        }

        log.debug("Read member {}", member);

        return member;
    }

    @Override
    public final MemberContact patch(final MemberContact member) {
        final MemberContact existing;
        final MemberContact toSave;
        final MemberContact saved;

        log.debug("Patching member {} using data {}", member.number(), member);

        // TODO: Identificator must be unique or empty
        // TODO: Apply the creation validations

        existing = memberContactRepository.findOne(member.number())
            .orElseThrow(() -> {
                log.error("Missing member {}", member.number());
                throw new MissingMemberException(member.number());
            });

        // TODO: maybe send an exception with all
        member.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        toSave = copy(existing, member);

        patchMemberValidator.validate(toSave);

        saved = memberContactRepository.save(toSave);

        log.debug("Patched member {}: {}", member.number(), saved);

        return saved;
    }

    @Override
    public final MemberContact update(final MemberContact member) {
        final MemberContact saved;

        log.debug("Updating member {} using data {}", member.number(), member);

        // TODO: Identificator must be unique or empty
        // TODO: The membership maybe can't be removed

        if (!contactRepository.exists(member.number())) {
            log.error("Missing member {}", member.number());
            throw new MissingMemberException(member.number());
        }

        // TODO: maybe send an exception with all
        member.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        updateMemberValidator.validate(member);

        saved = memberContactRepository.save(member);

        log.debug("Updated member {}: {}", member.number(), saved);

        return saved;
    }

    private final void checkContactMethodExists(final ContactMethod contactMethod) {
        if (!contactMethodRepository.exists(contactMethod.number())) {
            log.error("Missing contact method {}", contactMethod.number());
            throw new MissingContactMethodException(contactMethod.number());
        }
    }

    private final MemberContact copy(final MemberContact existing, final MemberContact updated) {
        final ContactName name;

        if (updated.name() == null) {
            name = existing.name();
        } else {
            name = new ContactName(Optional.ofNullable(updated.name()
                .firstName())
                .orElse(existing.name()
                    .firstName()),
                Optional.ofNullable(updated.name()
                    .lastName())
                    .orElse(existing.name()
                        .lastName()));
        }
        return new MemberContact(Optional.ofNullable(updated.identifier())
            .orElse(existing.identifier()),
            Optional.ofNullable(updated.number())
                .orElse(existing.number()),
            name, Optional.ofNullable(updated.birthDate())
                .orElse(existing.birthDate()),
            Optional.ofNullable(updated.active())
                .orElse(existing.active()),
            Optional.ofNullable(updated.renew())
                .orElse(existing.renew()),
            updated.contactChannels());
    }

}
