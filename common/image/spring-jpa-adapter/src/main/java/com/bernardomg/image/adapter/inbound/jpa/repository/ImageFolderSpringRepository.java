/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.image.adapter.inbound.jpa.model.ImageFolderEntity;

public interface ImageFolderSpringRepository extends JpaRepository<ImageFolderEntity, Long> {

    public void deleteByNumber(final Long number);

    @Query("""
            SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END FROM ImageFolder f \
            WHERE f.name = :name AND (:excluded IS NULL OR f.number != :excluded) \
            AND ((:parent IS NULL AND f.parent IS NULL) OR f.parent.number = :parent)""")
    public boolean existsByNameAndParent(@Param("name") final String name, @Param("parent") final Long parentNumber,
            @Param("excluded") final Long excludedNumber);

    public boolean existsByParentNumber(final Long parentNumber);

    public Optional<ImageFolderEntity> findByNumber(final Long number);

    @Query("SELECT COALESCE(MAX(f.number), 0) + 1 FROM ImageFolder f")
    public Long findNextNumber();

}
