
package com.bernardomg.association.member.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.bernardomg.association.member.model.DtoMemberPeriod;
import com.bernardomg.association.member.model.MemberPeriod;
import com.bernardomg.association.member.model.PersistentMemberPeriod;
import com.bernardomg.association.member.repository.MemberPeriodRepository;
import com.bernardomg.association.member.repository.MemberRepository;
import com.bernardomg.association.member.validation.MemberPeriodValidator;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.Validator;
import com.bernardomg.validation.exception.ValidationException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultMemberPeriodService implements MemberPeriodService {

    private final Validator<MemberPeriod> periodValidator = new MemberPeriodValidator();

    private final MemberPeriodRepository  repository;
    
    private final MemberRepository memberRepository;

    @Override
    public final MemberPeriod create(final Long member, final MemberPeriod period) {
        final PersistentMemberPeriod entity;
        final PersistentMemberPeriod created;

        // TODO: Check the member exists

        periodValidator.validate(period);

        validateOverlapped(member, period);
        validateMemberExists(member);

        entity = toPersistentMemberPeriod(period);
        entity.setMember(member);

        created = repository.save(entity);
        return toMemberPeriod(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public final Iterable<? extends MemberPeriod> getAllForMember(final Long member) {
        final Example<PersistentMemberPeriod> example;
        final PersistentMemberPeriod          entity;

        entity = new PersistentMemberPeriod();
        entity.setMember(member);

        example = Example.of(entity);

        // TODO: Sort by date
        return repository.findAll(example)
            .stream()
            .map(this::toMemberPeriod)
            .collect(Collectors.toList());
    }

    @Override
    public final MemberPeriod update(final Long member, final Long id, final MemberPeriod period) {
        final PersistentMemberPeriod entity;
        final PersistentMemberPeriod updated;

        periodValidator.validate(period);

        validateOverlapped(member, period);
        validateMemberExists(member);

        entity = toPersistentMemberPeriod(period);
        entity.setId(id);
        entity.setMember(member);

        updated = repository.save(entity);
        return toMemberPeriod(updated);
    }

    private final MemberPeriod toMemberPeriod(final PersistentMemberPeriod entity) {
        final DtoMemberPeriod data;

        data = new DtoMemberPeriod();
        data.setId(entity.getId());
        data.setMember(entity.getMember());
        data.setStartMonth(entity.getStartMonth());
        data.setStartYear(entity.getStartYear());
        data.setEndMonth(entity.getEndMonth());
        data.setEndYear(entity.getEndYear());

        return data;
    }

    private final PersistentMemberPeriod toPersistentMemberPeriod(final MemberPeriod data) {
        final PersistentMemberPeriod entity;

        entity = new PersistentMemberPeriod();
        entity.setId(data.getId());
        entity.setMember(data.getMember());
        entity.setStartMonth(data.getStartMonth());
        entity.setStartYear(data.getStartYear());
        entity.setEndMonth(data.getEndMonth());
        entity.setEndYear(data.getEndYear());

        return entity;
    }

    private final void validateOverlapped(final Long member, final MemberPeriod period) {
        final Collection<PersistentMemberPeriod> overlapped;

        // TODO: Move to validator if possible
        overlapped = repository.findOverlapped(member, period.getStartMonth(), period.getStartYear(),
            period.getEndMonth(), period.getEndYear());

        if (!overlapped.isEmpty()) {
            throw new ValidationException(ValidationError.of("error.memberPeriod.overlapsExisting"));
        }
    }
    private final void validateMemberExists(final Long member) {
        // TODO: Move to validator if possible
        if(!memberRepository.existsById(member)) {
            throw new ValidationException(ValidationError.of("error.member.notExists"));
        }
    }

}
