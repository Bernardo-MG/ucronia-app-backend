
package com.bernardomg.asset.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetEntity;
import com.bernardomg.asset.adapter.inbound.jpa.model.AssetType;

public interface AssetSpringRepository extends JpaRepository<AssetEntity, Long> {

    public void deleteByTypeAndNumber(final AssetType type, final long number);

    public boolean existsByTypeAndFolderNumber(final AssetType type, final long folderNumber);

    public boolean existsByTypeAndNameAndFolderIsNull(final AssetType type, final String name);

    public boolean existsByTypeAndNameAndFolderNumber(final AssetType type, final String name, final long folderNumber);

    public boolean existsByTypeAndNameAndFolderNumberAndNumberNot(final AssetType type, final String name,
            final long folderNumber, final long excludedNumber);

    public boolean existsByTypeAndNameAndNumberNotAndFolderIsNull(final AssetType type, final String name,
            final long excludedNumber);

    public boolean existsByTypeAndNumber(final AssetType type, final long number);

    public Page<AssetEntity> findAllByType(final AssetType type, final Pageable pageable);

    public Page<AssetEntity> findAllByTypeAndFolderIsNull(final AssetType type, final Pageable pageable);

    public Page<AssetEntity> findAllByTypeAndFolderIsNullAndPublicAccessTrue(final AssetType type,
            final Pageable pageable);

    public Page<AssetEntity> findAllByTypeAndFolderNumber(final AssetType type, final long folderNumber,
            final Pageable pageable);

    public Page<AssetEntity> findAllByTypeAndFolderNumberAndPublicAccessTrue(final AssetType type,
            final long folderNumber, final Pageable pageable);

    public Page<AssetEntity> findAllByTypeAndPublicAccessTrue(final AssetType type, final Pageable pageable);

    public Optional<AssetEntity> findByTypeAndNumber(final AssetType type, final long number);

    @Query("SELECT COALESCE(MAX(a.number), 0) + 1 FROM Asset a WHERE a.type = :type")
    public long findNextNumber(@Param("type") final AssetType type);
}
