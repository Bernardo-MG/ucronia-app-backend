/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.image.adapter.inbound.jpa.model.ImageEntity;

public interface ImageSpringRepository extends JpaRepository<ImageEntity, Long> {

    public void deleteByNumber(final long number);

    public boolean existsByFolderNumber(final long folderNumber);

    public boolean existsByName(final String name);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN TRUE ELSE FALSE END FROM Image i WHERE i.number != :number AND i.name = :name")
    public boolean existsByNotNumberAndName(@Param("number") final long number, @Param("name") final String name);

    public boolean existsByNumber(final long number);

    public Page<ImageEntity> findAllByFolderIsNull(final Pageable pageable);

    public Page<ImageEntity> findAllByFolderNumber(final long folderNumber, final Pageable pageable);

    public Optional<ImageEntity> findByNumber(final long number);

    @Query("SELECT COALESCE(MAX(i.number), 0) + 1 FROM Image i")
    public long findNextNumber();
}
