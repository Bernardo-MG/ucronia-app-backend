
package com.bernardomg.association.calendar.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.GameTableEntity;

public interface GameTableSpringRepository extends JpaRepository<GameTableEntity, Long> {

    public boolean existsByNumber(final long number);

    public Optional<GameTableEntity> findByNumber(Long number);

    @Query("""
            SELECT COALESCE(MAX(t.number), 0) + 1
            FROM GameTable t
            """)
    public Long findNextNumber();

}
