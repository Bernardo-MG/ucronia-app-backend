
package com.bernardomg.association.fee.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bernardomg.association.fee.model.DtoFee;
import com.bernardomg.association.fee.model.Fee;
import com.bernardomg.association.fee.model.PersistentFee;
import com.bernardomg.association.fee.repository.FeeRepository;
import com.bernardomg.association.fee.validation.FeeValidator;

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
    public final Fee create(final Fee month) {
        final PersistentFee entity;
        final PersistentFee created;

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
    public final Iterable<? extends Fee> getAll(final Fee sample) {
        final PersistentFee entity;
        final Sort          sort;

        entity = toEntity(sample);

        sort = Sort.by(Direction.ASC, "month", "year");

        return repository.findAll(Example.of(entity), sort)
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public final Optional<? extends Fee> getOne(final Long id) {
        final Optional<PersistentFee> found;
        final Optional<? extends Fee> result;
        final Fee                     member;

        found = repository.findById(id);

        if (found.isPresent()) {
            member = toDto(found.get());
            result = Optional.of(member);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Fee update(final Long member, final Fee month) {
        final PersistentFee entity;
        final PersistentFee created;

        validator.validate(month);

        entity = toEntity(month);
        entity.setMember(member);

        created = repository.save(entity);
        return toDto(created);
    }

    private final Fee toDto(final PersistentFee entity) {
        final DtoFee data;

        data = new DtoFee();
        data.setId(entity.getId());
        data.setMember(entity.getMember());
        data.setMonth(entity.getMonth());
        data.setYear(entity.getYear());
        data.setPaid(entity.getPaid());

        return data;
    }

    private final PersistentFee toEntity(final Fee data) {
        final PersistentFee entity;

        entity = new PersistentFee();
        entity.setId(data.getId());
        entity.setMember(data.getMember());
        entity.setMonth(data.getMonth());
        entity.setYear(data.getYear());
        entity.setPaid(data.getPaid());

        return entity;
    }

}
