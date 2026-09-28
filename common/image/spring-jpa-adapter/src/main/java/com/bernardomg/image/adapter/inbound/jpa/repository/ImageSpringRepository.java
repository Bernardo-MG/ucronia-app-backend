
package com.bernardomg.image.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetEntity;

public interface ImageSpringRepository extends JpaRepository<AssetEntity, Long> {

    public void deleteByNumber(final long number);

    public boolean existsByFolderNumber(final long folderNumber);

    public boolean existsByNameAndFolderIsNull(final String name);

    public boolean existsByNameAndFolderNumber(final String name, final long folderNumber);

    public boolean existsByNameAndFolderNumberAndNumberNot(final String name, final long folderNumber,
            final long excludedNumber);

    public boolean existsByNameAndNumberNotAndFolderIsNull(final String name, final long excludedNumber);

    public boolean existsByNumber(final long number);

    public Page<AssetEntity> findAllByFolderIsNull(final Pageable pageable);

    public Page<AssetEntity> findAllByFolderIsNullAndPublicAccessTrue(final Pageable pageable);

    public Page<AssetEntity> findAllByFolderNumber(final long folderNumber, final Pageable pageable);

    public Page<AssetEntity> findAllByFolderNumberAndPublicAccessTrue(final long folderNumber, final Pageable pageable);

    public Page<AssetEntity> findAllByPublicAccessTrue(final Pageable pageable);

    public Optional<AssetEntity> findByNumber(final long number);

    @Query("SELECT COALESCE(MAX(a.number), 0) + 1 FROM Asset a")
    public long findNextNumber();
}
