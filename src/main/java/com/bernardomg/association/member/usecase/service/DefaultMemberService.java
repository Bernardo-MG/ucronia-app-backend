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
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.filter.MemberQuery;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.usecase.validation.MemberIdentifierNotExistForAnotherRule;
import com.bernardomg.association.member.usecase.validation.MemberIdentifierNotExistRule;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultMemberService implements MemberService {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(DefaultMemberService.class);

    private final ContactMethodRepository contactMethodRepository;

    private final Validator<Member>       createMemberValidator;

    private final MemberRepository        memberRepository;

    private final Validator<Member>       patchMemberValidator;

    private final Validator<Member>       updateMemberValidator;

    public DefaultMemberService(final MemberRepository memberRepo, final ContactMethodRepository contactMethodRepo) {
        super();

        memberRepository = Objects.requireNonNull(memberRepo);
        contactMethodRepository = Objects.requireNonNull(contactMethodRepo);
        createMemberValidator = new FieldRuleValidator<>(new MemberIdentifierNotExistRule(memberRepo));
        updateMemberValidator = new FieldRuleValidator<>(new MemberIdentifierNotExistForAnotherRule(memberRepo));
        patchMemberValidator = new FieldRuleValidator<>(new MemberIdentifierNotExistForAnotherRule(memberRepo));
    }

    @Override
    public final Member create(final Member member) {
        final Member toCreate;
        final Member created;
        final Long   number;

        log.debug("Creating member {}", member);

        // TODO: maybe send an exception with all
        member.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        // Set number
        number = memberRepository.findNextNumber();

        toCreate = new Member(member.identifier(), number, member.name(), member.birthDate(), member.active(),
            member.renew(), member.contactChannels());

        createMemberValidator.validate(toCreate);

        created = memberRepository.save(toCreate);

        log.debug("Created member {}", created);

        return created;
    }

    @Override
    public final Member delete(final long number) {
        final Member existing;

        log.debug("Deleting member {}", number);

        existing = memberRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing member {}", number);
                throw new MissingMemberException(number);
            });

        memberRepository.delete(number);

        log.debug("Deleted member {}", number);

        return existing;
    }

    @Override
    public final Page<Member> getAll(final MemberQuery filter, final Pagination pagination, final Sorting sorting) {
        final Page<Member> read;

        log.debug("Reading members with query {}, pagination {} and sorting {}", filter, pagination, sorting);

        read = memberRepository.findAll(filter, pagination, sorting);

        log.debug("Read members with query {}, pagination {} and sorting {}: {}", filter, pagination, sorting, read);

        return read;
    }

    @Override
    public final Optional<Member> getOne(final long number) {
        final Optional<Member> member;

        log.debug("Reading member {}", number);

        member = memberRepository.findOne(number);
        if (member.isEmpty()) {
            log.error("Missing member {}", number);
            throw new MissingMemberException(number);
        }

        log.debug("Read member {}", member);

        return member;
    }

    @Override
    public final Member patch(final Member member) {
        final Member existing;
        final Member toSave;
        final Member saved;

        log.debug("Patching member {} using data {}", member.number(), member);

        // TODO: Identificator must be unique or empty
        // TODO: Apply the creation validations

        existing = memberRepository.findOne(member.number())
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

        saved = memberRepository.save(toSave);

        log.debug("Patched member {}: {}", member.number(), saved);

        return saved;
    }

    @Override
    public final Member update(final Member member) {
        final Member saved;

        log.debug("Updating member {} using data {}", member.number(), member);

        // TODO: Identificator must be unique or empty
        // TODO: The membership maybe can't be removed

        if (!memberRepository.exists(member.number())) {
            log.error("Missing member {}", member.number());
            throw new MissingMemberException(member.number());
        }

        // TODO: maybe send an exception with all
        member.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        updateMemberValidator.validate(member);

        saved = memberRepository.save(member);

        log.debug("Updated member {}: {}", member.number(), saved);

        return saved;
    }

    private final void checkContactMethodExists(final ContactMethod contactMethod) {
        if (!contactMethodRepository.exists(contactMethod.number())) {
            log.error("Missing contact method {}", contactMethod.number());
            throw new MissingContactMethodException(contactMethod.number());
        }
    }

    private final Member copy(final Member existing, final Member updated) {
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
        return new Member(Optional.ofNullable(updated.identifier())
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
