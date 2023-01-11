
package com.bernardomg.association.fee.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.fee.model.DtoMemberFee;
import com.bernardomg.association.fee.model.FeeForm;
import com.bernardomg.association.fee.model.FeeRequest;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.PersistentFee;
import com.bernardomg.association.fee.repository.FeeRepository;
import com.bernardomg.association.fee.repository.MemberFeeRepository;
import com.bernardomg.association.member.repository.MemberRepository;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the fee service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
@Slf4j
public final class DefaultFeeService implements FeeService {

    private final MemberFeeRepository memberFeeRepository;

    private final MemberRepository    memberRepository;

    private final FeeRepository       repository;

    @Override
    @PreAuthorize("hasAuthority('CREATE_FEE')")
    public final MemberFee create(final FeeForm form) {
        final PersistentFee            entity;
        final PersistentFee            created;
        final Collection<FieldFailure> failures;

        failures = validateCreate(form);

        // TODO: Validate that the entity doesn't exist, or handle DB exceptions
        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        entity = toEntity(form);
        entity.setId(null);

        created = repository.save(entity);

        return toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('DELETE_FEE')")
    public final Boolean delete(final Long id) {
        Boolean deleted;

        try {
            repository.deleteById(id);
            deleted = true;
        } catch (final EmptyResultDataAccessException e) {
            log.error("Tried to delete id {}, which doesn't exist", id);
            deleted = false;
        }

        return deleted;
    }

    @Override
    @PreAuthorize("hasAuthority('READ_FEE')")
    public final Iterable<? extends MemberFee> getAll(final FeeRequest request, final Pageable pageable) {
        // TODO: Test repository
        // TODO: Test reading with no name or surname
        return memberFeeRepository.findAllWithMember(request, pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('READ_FEE')")
    public final Optional<? extends MemberFee> getOne(final Long id) {
        final Optional<MemberFee>           found;
        final Optional<? extends MemberFee> result;
        final MemberFee                     member;

        // TODO: Test repository
        // TODO: Test reading with no name or surname
        found = memberFeeRepository.findOneByIdWithMember(id);

        if (found.isPresent()) {
            member = found.get();
            result = Optional.of(member);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    @PreAuthorize("hasAuthority('UPDATE_FEE')")
    public final MemberFee update(final Long id, final FeeForm form) {
        final PersistentFee            entity;
        final PersistentFee            created;
        final Collection<FieldFailure> failures;

        failures = validateUpdate(form);

        // TODO: Validate that the entity doesn't exist, or handle DB exceptions
        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        entity = toEntity(form);
        entity.setId(id);

        created = repository.save(entity);
        return toDto(created);
    }

    private final Calendar removeDay(final Calendar calendar) {
        final Integer year;
        final Integer month;

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);

        return new GregorianCalendar(year, month, 1);
    }

    private final MemberFee toDto(final PersistentFee entity) {
        final DtoMemberFee data;
        final Calendar     date;

        if (entity.getDate() != null) {
            date = removeDay(entity.getDate());
        } else {
            date = null;
        }

        data = new DtoMemberFee();
        data.setId(entity.getId());
        data.setMemberId(entity.getMemberId());
        data.setDate(date);
        data.setPaid(entity.getPaid());

        return data;
    }

    private final PersistentFee toEntity(final FeeForm data) {
        final PersistentFee entity;
        final Calendar      date;

        entity = new PersistentFee();
        entity.setMemberId(data.getMemberId());
        entity.setPaid(data.getPaid());

        date = removeDay(data.getDate());
        entity.setDate(date);

        return entity;
    }

    private final Collection<FieldFailure> validateCreate(final FeeForm form) {
        final Collection<FieldFailure> failures;
        final FieldFailure             failure;

        failures = new ArrayList<>();

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        if (!memberRepository.existsById(form.getMemberId())) {
            log.error("Found no member with id {}", form.getMemberId());
            failure = FieldFailure.of("memberId", "notExists", form.getMemberId());
            failures.add(failure);
        }

        return failures;
    }

    private final Collection<FieldFailure> validateUpdate(final FeeForm form) {
        final Collection<FieldFailure> failures;
        final FieldFailure             failure;

        failures = new ArrayList<>();

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        // TODO: Test validation
        if (!memberRepository.existsById(form.getMemberId())) {
            log.error("Found no member with id {}", form.getMemberId());
            failure = FieldFailure.of("memberId", "notExists", form.getMemberId());
            failures.add(failure);
        }

        return failures;
    }

}
