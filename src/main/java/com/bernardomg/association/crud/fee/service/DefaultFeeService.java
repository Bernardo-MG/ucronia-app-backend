
package com.bernardomg.association.crud.fee.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.association.crud.fee.model.DtoFee;
import com.bernardomg.association.crud.fee.model.Fee;
import com.bernardomg.association.crud.fee.model.FeeForm;
import com.bernardomg.association.crud.fee.model.PersistentFee;
import com.bernardomg.association.crud.fee.repository.FeeRepository;
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

    private final FeeRepository repository;

    private final FeeValidator  validator;

    @Override
    public final Fee create(final FeeForm month) {
        final PersistentFee entity;
        final PersistentFee created;

        // TODO: Validate that the entity doesn't exist, or handle DB exceptions
        validator.validate(month);

        entity = toEntity(month);

        created = repository.save(entity);
        return toDto(created);
    }

    @Override
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
    public final Iterable<? extends Fee> getAll(final Fee sample, final Pageable pageable) {
        final PersistentFee entity;

        entity = toEntity(sample);

        // TODO: Test repository
        // TODO: Test reading with no name or surname
        return repository.findAllWithMember(Example.of(entity), pageable);
    }

    @Override
    public final Optional<? extends Fee> getOne(final Long id) {
        final Optional<Fee>           found;
        final Optional<? extends Fee> result;
        final Fee                     member;

        // TODO: Test repository
        // TODO: Test reading with no name or surname
        found = repository.findByIdWithMember(id);

        if (found.isPresent()) {
            member = found.get();
            result = Optional.of(member);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Fee update(final Long id, final FeeForm fee) {
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

    private final Fee toDto(final PersistentFee entity) {
        final DtoFee   data;
        final Calendar date;

        if (entity.getPayDate() != null) {
            date = removeDay(entity.getPayDate());
        } else {
            date = null;
        }

        data = new DtoFee();
        data.setId(entity.getId());
        data.setMemberId(entity.getMemberId());
        data.setPayDate(date);
        data.setPaid(entity.getPaid());

        return data;
    }

    private final PersistentFee toEntity(final Fee data) {
        final PersistentFee entity;
        final Calendar      date;

        if (data.getPayDate() != null) {
            date = removeDay(data.getPayDate());
        } else {
            date = null;
        }

        entity = new PersistentFee();
        entity.setId(data.getId());
        entity.setMemberId(data.getMemberId());
        entity.setPayDate(date);
        entity.setPaid(data.getPaid());

        return entity;
    }

    private final PersistentFee toEntity(final FeeForm data) {
        final PersistentFee entity;
        final Calendar      date;

        if (data.getPayDate() != null) {
            date = removeDay(data.getPayDate());
        } else {
            date = null;
        }

        entity = new PersistentFee();
        entity.setId(data.getId());
        entity.setMemberId(data.getMemberId());
        entity.setPayDate(date);
        entity.setPaid(data.getPaid());

        return entity;
    }

}
