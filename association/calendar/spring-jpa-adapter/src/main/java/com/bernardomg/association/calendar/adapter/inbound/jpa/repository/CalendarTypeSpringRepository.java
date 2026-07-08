
package com.bernardomg.association.calendar.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarTypeEntity;

public interface CalendarTypeSpringRepository extends JpaRepository<CalendarTypeEntity, Long> {

    public Optional<CalendarTypeEntity> findByNumber(Long number);

}
