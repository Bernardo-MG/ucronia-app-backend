
package com.bernardomg.association.calendar.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.GameTableEntity;

public interface GameTableSpringRepository extends JpaRepository<GameTableEntity, Long> {

    public Optional<GameTableEntity> findByNumber(Long number);

}
