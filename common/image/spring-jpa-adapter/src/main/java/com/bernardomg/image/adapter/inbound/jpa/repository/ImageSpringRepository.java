
package com.bernardomg.image.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.image.adapter.inbound.jpa.model.ImageEntity;

public interface ImageSpringRepository extends JpaRepository<ImageEntity, Long> {

    public void deleteByNumber(final long number);

    public boolean existsByFolderNumber(final long folderNumber);

    public boolean existsByNameAndFolderIsNull(final String name);

    public boolean existsByNameAndFolderNumber(final String name, final long folderNumber);

    public boolean existsByNameAndFolderNumberAndNumberNot(final String name, final long folderNumber,
            final long excludedNumber);

    public boolean existsByNameAndNumberNotAndFolderIsNull(final String name, final long excludedNumber);

    public boolean existsByNumber(final long number);

    public Page<ImageEntity> findAllByFolderIsNull(final Pageable pageable);

    public Page<ImageEntity> findAllByFolderNumber(final long folderNumber, final Pageable pageable);

    public Optional<ImageEntity> findByNumber(final long number);

    @Query("SELECT COALESCE(MAX(i.number), 0) + 1 FROM Image i")
    public long findNextNumber();
}
