
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

import com.bernardomg.association.fee.model.DtoMemberFee;
import com.bernardomg.association.fee.model.FeeForm;
import com.bernardomg.association.fee.model.FeeRequest;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.PersistentFee;
import com.bernardomg.association.fee.model.PersistentMemberFee;
import com.bernardomg.association.fee.repository.FeeRepository;
import com.bernardomg.association.fee.repository.MemberFeeRepository;
import com.bernardomg.association.fee.repository.MemberFeeSpecifications;
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

    private final FeeRepository       feeRepository;

    private final MemberFeeRepository memberFeeRepository;

    private final MemberRepository    memberRepository;

    @Override
    @PreAuthorize("hasAuthority('FEE:CREATE')")
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

        created = feeRepository.save(entity);

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
    public final Iterable<MemberFee> getAll(final FeeRequest request, final Pageable pageable) {
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

        created = feeRepository.save(entity);
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

    private final MemberFee toDto(final PersistentMemberFee entity) {
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
        data.setName(entity.getName());
        data.setSurname(entity.getSurname());

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
