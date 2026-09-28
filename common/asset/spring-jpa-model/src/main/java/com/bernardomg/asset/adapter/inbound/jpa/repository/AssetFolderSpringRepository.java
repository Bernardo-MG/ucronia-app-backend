
package com.bernardomg.asset.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetFolderEntity;
import com.bernardomg.asset.adapter.inbound.jpa.model.AssetType;

public interface AssetFolderSpringRepository extends JpaRepository<AssetFolderEntity, Long> {

    public void deleteByTypeAndNumber(final AssetType type, final Long number);

    public boolean existsByTypeAndNameAndParentIsNull(final AssetType type, final String name);

    public boolean existsByTypeAndNameAndParentIsNullAndNumberNot(final AssetType type, final String name,
            final Long number);

    public boolean existsByTypeAndNameAndParentNumber(final AssetType type, final String name, final Long parentNumber);

    public boolean existsByTypeAndNameAndParentNumberAndNumberNot(final AssetType type, final String name,
            final Long parentNumber, final Long number);

    public boolean existsByTypeAndNumber(final AssetType type, final Long number);

    public boolean existsByTypeAndParentNumber(final AssetType type, final Long parentNumber);

    public Collection<AssetFolderEntity> findAllByType(final AssetType type);

    public Optional<AssetFolderEntity> findByTypeAndNumber(final AssetType type, final Long number);

    @Query("SELECT COALESCE(MAX(f.number), 0) + 1 FROM AssetFolder f WHERE f.type = :type")
    public Long findNextNumber(@Param("type") final AssetType type);

}
