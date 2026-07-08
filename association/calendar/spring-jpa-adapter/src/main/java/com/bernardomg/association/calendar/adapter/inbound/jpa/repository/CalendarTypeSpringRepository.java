
package com.bernardomg.association.calendar.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarTypeEntity;

public interface CalendarTypeSpringRepository extends JpaRepository<CalendarTypeEntity, Long> {

    public Optional<CalendarTypeEntity> findByNumber(Long number);

    @Query("SELECT COALESCE(MAX(t.number), 0) + 1 FROM CalendarType t")
    public Long findNextNumber();

    public boolean existsByNumber(final long number);

}
