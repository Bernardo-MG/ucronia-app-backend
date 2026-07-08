
package com.bernardomg.association.calendar.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarTypeEntity;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarTypeEntityMapper;
import com.bernardomg.association.calendar.domain.model.CalendarType;
import com.bernardomg.association.calendar.domain.repository.CalendarTypeRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringPagination;

public final class JpaCalendarTypeRepository implements CalendarTypeRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                log = LoggerFactory.getLogger(JpaCalendarTypeRepository.class);

    private final CalendarTypeSpringRepository calendarTypeSpringRepository;

    public JpaCalendarTypeRepository(final CalendarTypeSpringRepository calendarTypeSpringRepo) {
        super();

        calendarTypeSpringRepository = Objects.requireNonNull(calendarTypeSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        final Optional<CalendarTypeEntity> calendar;

        log.debug("Deleting calendar type {}", number);

        calendar = calendarTypeSpringRepository.findByNumber(number);
        if (calendar.isPresent()) {
            calendarTypeSpringRepository.deleteById(calendar.get()
                .getId());

            log.debug("Deleted calendar type {}", number);
        } else {
            log.debug("Couldn't delete calendar type {} as it doesn't exist", number);
        }
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if activity {} exists", number);

        exists = calendarTypeSpringRepository.existsByNumber(number);

        log.debug("Activity {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<CalendarType> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<CalendarTypeEntity> page;
        final org.springframework.data.domain.Page<CalendarType>       read;
        final Pageable                                                 pageable;

        log.debug("Finding activities with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        page = calendarTypeSpringRepository.findAll(pageable);

        read = page.map(CalendarTypeEntityMapper::toDomain);

        log.debug("Found activities {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<CalendarType> findOne(final Long number) {
        final Optional<CalendarType> activity;

        log.debug("Finding activity with number {}", number);

        activity = calendarTypeSpringRepository.findByNumber(number)
            .map(CalendarTypeEntityMapper::toDomain);

        log.debug("Found activity with number {}: {}", number, activity);

        return activity;
    }

    @Override
    public final CalendarType save(final CalendarType calendarType) {
        final Optional<CalendarTypeEntity> existing;
        final CalendarTypeEntity           entity;
        final CalendarTypeEntity           created;
        final CalendarType                 saved;
        final Long                         number;

        log.debug("Saving activity {}", calendarType);

        entity = CalendarTypeEntityMapper.toEntity(calendarType);

        existing = calendarTypeSpringRepository.findByNumber(calendarType.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        } else {
            number = calendarTypeSpringRepository.findNextNumber();
            entity.setNumber(number);
        }

        created = calendarTypeSpringRepository.save(entity);
        saved = CalendarTypeEntityMapper.toDomain(created);

        log.debug("Saved activity {}", saved);

        return saved;
    }

}
