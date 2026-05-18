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

import com.bernardomg.association.member.domain.exception.MissingMemberContactMethodException;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.exception.MissingMemberFeeTypeException;
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.Member.ContactChannel;
import com.bernardomg.association.member.domain.model.Member.ContactMethod;
import com.bernardomg.association.member.domain.model.Member.Name;
import com.bernardomg.association.member.domain.repository.MemberContactMethodRepository;
import com.bernardomg.association.member.domain.repository.MemberFeeTypeRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.usecase.validation.MemberIdentifierNotExistForAnotherRule;
import com.bernardomg.association.member.usecase.validation.MemberIdentifierNotExistRule;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import jakarta.transaction.Transactional;

/**
 * Default implementation of the member profile service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Transactional
public final class DefaultMemberService implements MemberService {

    /**
     * Logger for the class.
     */
    private static final Logger                 log = LoggerFactory.getLogger(DefaultMemberService.class);

    private final MemberContactMethodRepository contactMethodRepository;

    private final Validator<Member>             createValidator;

    private final MemberFeeTypeRepository       memberFeeTypeRepository;

    private final MemberRepository              memberRepository;

    private final Validator<Member>             patchValidator;

    private final Validator<Member>             updateValidator;

    public DefaultMemberService(final MemberRepository memberRepo,
            final MemberContactMethodRepository contactMethodRepo, final MemberFeeTypeRepository memberFeeTypeRepo) {
        super();

        memberRepository = Objects.requireNonNull(memberRepo);
        contactMethodRepository = Objects.requireNonNull(contactMethodRepo);
        memberFeeTypeRepository = Objects.requireNonNull(memberFeeTypeRepo);

        createValidator = new FieldRuleValidator<>(new MemberIdentifierNotExistRule(memberRepo));
        updateValidator = new FieldRuleValidator<>(new MemberIdentifierNotExistForAnotherRule(memberRepo));
        patchValidator = new FieldRuleValidator<>(new MemberIdentifierNotExistForAnotherRule(memberRepo));
    }

    @Override
    public final Member create(final Member member) {
        final Member created;

        log.debug("Creating member profile {}", member);

        if (!memberFeeTypeRepository.exists(member.feeType()
            .number())) {
            log.error("Missing fee type {}", member.feeType()
                .number());
            throw new MissingMemberFeeTypeException(member.feeType()
                .number());
        }

        // TODO: maybe send an exception with all
        member.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        createValidator.validate(member);

        created = memberRepository.save(member);

        log.debug("Created member profile {}", created);

        return created;
    }

    @Override
    public final Member delete(final long number) {
        final Member existing;

        log.debug("Deleting member profile {}", number);

        existing = memberRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing member profile {}", number);
                throw new MissingMemberException(number);
            });

        memberRepository.delete(number);

        log.debug("Deleted member profile {}", number);

        return existing;
    }

    @Override
    public final Page<Member> getAll(final MemberFilter filter, final Pagination pagination, final Sorting sorting) {
        final Page<Member> members;

        log.debug("Reading member contacts with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        members = memberRepository.findAll(filter, pagination, sorting);

        log.debug("Read member contacts with filter {}, pagination {} and sorting {}: {}", filter, pagination, sorting,
            members);

        return members;
    }

    @Override
    public final Optional<Member> getOne(final long number) {
        final Optional<Member> members;

        log.debug("Reading member profile {}", number);

        members = memberRepository.findOne(number);
        if (members.isEmpty()) {
            log.error("Missing member profile {}", number);
            throw new MissingMemberException(number);
        }

        log.debug("Read member profile {}: {}", number, members);

        return members;
    }

    @Override
    public final Member patch(final Member member) {
        final Member existing;
        final Member toSave;
        final Member saved;

        log.debug("Patching member profile {} using data {}", member.number(), member);

        existing = memberRepository.findOne(member.number())
            .orElseThrow(() -> {
                log.error("Missing member profile {}", member.number());
                throw new MissingMemberException(member.number());
            });

        if (!memberFeeTypeRepository.exists(member.feeType()
            .number())) {
            log.error("Missing fee type {}", member.feeType()
                .number());
            throw new MissingMemberFeeTypeException(member.feeType()
                .number());
        }

        // TODO: maybe send an exception with all
        member.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        toSave = copy(existing, member);

        patchValidator.validate(toSave);

        saved = memberRepository.save(toSave);

        log.debug("Patched member profile {}: {}", member.number(), saved);

        return saved;
    }

    @Override
    public final Member update(final Member member) {
        final Member saved;

        log.debug("Updating member profile {} using data {}", member.number(), member);

        if (!memberRepository.exists(member.number())) {
            log.error("Missing member profile {}", member.number());
            throw new MissingMemberException(member.number());
        }

        if (!memberFeeTypeRepository.exists(member.feeType()
            .number())) {
            log.error("Missing fee type {}", member.feeType()
                .number());
            throw new MissingMemberFeeTypeException(member.feeType()
                .number());
        }

        // TODO: maybe send an exception with all
        member.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        updateValidator.validate(member);

        saved = memberRepository.save(member);

        log.debug("Updated member profile {}: {}", member.number(), saved);

        return saved;
    }

    private final void checkContactMethodExists(final ContactMethod contactMethod) {
        if (!contactMethodRepository.exists(contactMethod.number())) {
            log.error("Missing contact method {}", contactMethod.number());
            throw new MissingMemberContactMethodException(contactMethod.number());
        }
    }

    private final Member copy(final Member existing, final Member updated) {
        final Name name;

        if (updated.name() == null) {
            name = existing.name();
        } else {
            name = new Name(Optional.ofNullable(updated.name()
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
            Optional.ofNullable(updated.contactChannels())
                .orElse(existing.contactChannels()),
            Optional.ofNullable(updated.address())
                .orElse(existing.address()),
            Optional.ofNullable(updated.comments())
                .orElse(existing.comments()),
            Optional.ofNullable(updated.active())
                .orElse(existing.active()),
            Optional.ofNullable(updated.renew())
                .orElse(existing.renew()),
            Optional.ofNullable(updated.feeType())
                .orElse(existing.feeType()),
            Optional.ofNullable(updated.types())
                .orElse(existing.types()));
    }

}
