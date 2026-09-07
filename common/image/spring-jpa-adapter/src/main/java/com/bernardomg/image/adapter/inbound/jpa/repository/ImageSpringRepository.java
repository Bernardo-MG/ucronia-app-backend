/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.image.adapter.inbound.jpa.model.ImageEntity;

public interface ImageSpringRepository extends JpaRepository<ImageEntity, Long> {

    public void deleteByNumber(final Long number);

    public boolean existsByName(final String name);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN TRUE ELSE FALSE END FROM Image i WHERE i.number != :number AND i.name = :name")
    public boolean existsByNotNumberAndName(@Param("number") final Long number, @Param("name") final String name);

    public Optional<ImageEntity> findByNumber(final Long number);

    @Query("SELECT COALESCE(MAX(i.number), 0) + 1 FROM Image i")
    public Long findNextNumber();
}
