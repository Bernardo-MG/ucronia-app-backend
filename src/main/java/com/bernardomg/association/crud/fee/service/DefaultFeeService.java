
package com.bernardomg.association.crud.fee.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.crud.fee.model.DtoMemberFee;
import com.bernardomg.association.crud.fee.model.FeeForm;
import com.bernardomg.association.crud.fee.model.MemberFee;
import com.bernardomg.association.crud.fee.model.PersistentFee;
import com.bernardomg.association.crud.fee.repository.FeeRepository;
import com.bernardomg.association.crud.fee.repository.MemberFeeRepository;
import com.bernardomg.association.crud.fee.validation.FeeValidator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the fee service.
 * <p>
 * Applies validation through the included {@link #validator}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
@Slf4j
public final class DefaultFeeService implements FeeService {

    private final MemberFeeRepository memberFeeRepository;

    private final FeeRepository       repository;

    private final FeeValidator        validator;

    @Override
    @PreAuthorize("hasAuthority('CREATE_FEE')")
    public final MemberFee create(final FeeForm month) {
        final PersistentFee entity;
        final PersistentFee created;

        // TODO: Validate that the entity doesn't exist, or handle DB exceptions
        validator.validate(month);

        entity = toEntity(month);
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
    public final Iterable<? extends MemberFee> getAll(final MemberFee sample, final Pageable pageable) {
        final PersistentFee entity;

        entity = toEntity(sample);

        // TODO: Test repository
        // TODO: Test reading with no name or surname
        return memberFeeRepository.findAllWithMember(Example.of(entity), pageable);
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
    public final MemberFee update(final Long id, final FeeForm fee) {
        final PersistentFee entity;
        final PersistentFee created;

        validator.validate(fee);

        entity = toEntity(fee);
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

        if (data.getDate() != null) {
            date = removeDay(data.getDate());
        } else {
            date = null;
        }

        entity = new PersistentFee();
        entity.setId(data.getId());
        entity.setMemberId(data.getMemberId());
        entity.setDate(date);
        entity.setPaid(data.getPaid());

        return entity;
    }

    private final PersistentFee toEntity(final MemberFee data) {
        final PersistentFee entity;
        final Calendar      date;

        if (data.getDate() != null) {
            date = removeDay(data.getDate());
        } else {
            date = null;
        }

        entity = new PersistentFee();
        entity.setId(data.getId());
        entity.setMemberId(data.getMemberId());
        entity.setDate(date);
        entity.setPaid(data.getPaid());

        return entity;
    }

}
