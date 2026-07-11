
package com.bernardomg.association.calendar.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.association.calendar.adapter.inbound.jpa.model.RecurrenceStatusEntity;
import com.bernardomg.association.calendar.domain.model.Recurrence.RecurrenceStatus;

public interface RecurrenceStatusSpringRepository extends JpaRepository<RecurrenceStatusEntity, Long> {

    Optional<RecurrenceStatusEntity> findByName(RecurrenceStatus status);

}
