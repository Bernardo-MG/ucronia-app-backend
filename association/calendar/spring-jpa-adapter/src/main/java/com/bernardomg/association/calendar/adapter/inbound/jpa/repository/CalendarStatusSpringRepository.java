
package com.bernardomg.association.calendar.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarStatusEntity;
import com.bernardomg.association.calendar.domain.model.CalendarStatus;

public interface CalendarStatusSpringRepository extends JpaRepository<CalendarStatusEntity, Long> {

    Optional<CalendarStatusEntity> findByName(CalendarStatus status);

}
