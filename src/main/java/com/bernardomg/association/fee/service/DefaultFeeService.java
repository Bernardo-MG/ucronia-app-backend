
package com.bernardomg.association.fee.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.fee.model.ImmutableMemberFee;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.request.FeeCreationRequest;
import com.bernardomg.association.fee.model.request.FeeQueryRequest;
import com.bernardomg.association.fee.model.request.FeeUpdateRequest;
import com.bernardomg.association.fee.persistence.model.PersistentFee;
import com.bernardomg.association.fee.persistence.model.PersistentMemberFee;
import com.bernardomg.association.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.fee.persistence.repository.MemberFeeRepository;
import com.bernardomg.association.fee.persistence.repository.MemberFeeSpecifications;
import com.bernardomg.association.member.persistence.repository.MemberRepository;
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

    private final FeeRepository       feeRepository;

    private final MemberFeeRepository memberFeeRepository;

    private final MemberRepository    memberRepository;

    @Override
    @PreAuthorize("hasAuthority('FEE:CREATE')")
    public final MemberFee create(final FeeCreationRequest request) {
        final PersistentFee            entity;
        final PersistentFee            created;
        final Collection<FieldFailure> failures;

        failures = validateCreate(request);

        // TODO: Validate that the entity doesn't exist, or handle DB exceptions
        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        entity = toEntity(request);
        entity.setId(null);

        created = feeRepository.save(entity);

        // TODO: Doesn't return names
        return toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('FEE:DELETE')")
    public final Boolean delete(final Long id) {
        feeRepository.deleteById(id);

        return true;
    }

    @Override
    @PreAuthorize("hasAuthority('FEE:READ')")
    public final Iterable<MemberFee> getAll(final FeeQueryRequest request, final Pageable pageable) {
        final Page<PersistentMemberFee>                    page;
        final Optional<Specification<PersistentMemberFee>> spec;
        // TODO: Test repository
        // TODO: Test reading with no name or surname

        spec = MemberFeeSpecifications.fromRequest(request);

        if (spec.isEmpty()) {
            page = memberFeeRepository.findAll(pageable);
        } else {
            page = memberFeeRepository.findAll(spec.get(), pageable);
        }

        return page.map(this::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('FEE:READ')")
    public final Optional<MemberFee> getOne(final Long id) {
        final Optional<PersistentMemberFee> found;
        final Optional<MemberFee>           result;

        // TODO: Test repository
        // TODO: Test reading with no name or surname
        found = memberFeeRepository.findById(id);

        if (found.isPresent()) {
            result = found.map(this::toDto);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    @PreAuthorize("hasAuthority('FEE:UPDATE')")
    public final MemberFee update(final Long id, final FeeUpdateRequest form) {
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

        created = feeRepository.save(entity);

        // TODO: Doesn't return names
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
        final Calendar date;

        if (entity.getDate() != null) {
            date = removeDay(entity.getDate());
        } else {
            date = null;
        }

        return ImmutableMemberFee.builder()
            .id(entity.getId())
            .memberId(entity.getMemberId())
            .date(date)
            .paid(entity.getPaid())
            .build();
    }

    private final MemberFee toDto(final PersistentMemberFee entity) {
        final Calendar date;

        if (entity.getDate() != null) {
            date = removeDay(entity.getDate());
        } else {
            date = null;
        }

        return ImmutableMemberFee.builder()
            .id(entity.getId())
            .memberId(entity.getMemberId())
            .date(date)
            .paid(entity.getPaid())
            .name(entity.getName())
            .surname(entity.getSurname())
            .build();
    }

    private final PersistentFee toEntity(final FeeCreationRequest request) {
        final Calendar date;

        if (request.getDate() != null) {
            date = removeDay(request.getDate());
        } else {
            date = null;
        }

        return PersistentFee.builder()
            .memberId(request.getMemberId())
            .paid(request.getPaid())
            .date(date)
            .build();
    }

    private final PersistentFee toEntity(final FeeUpdateRequest request) {
        final Calendar date;

        if (request.getDate() != null) {
            date = removeDay(request.getDate());
        } else {
            date = null;
        }

        return PersistentFee.builder()
            .id(request.getId())
            .memberId(request.getMemberId())
            .paid(request.getPaid())
            .date(date)
            .build();
    }

    private final Collection<FieldFailure> validateCreate(final FeeCreationRequest form) {
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

    private final Collection<FieldFailure> validateUpdate(final FeeUpdateRequest form) {
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
