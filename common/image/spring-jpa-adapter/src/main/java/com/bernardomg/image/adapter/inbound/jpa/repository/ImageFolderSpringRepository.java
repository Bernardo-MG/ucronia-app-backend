
package com.bernardomg.image.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetFolderEntity;

public interface ImageFolderSpringRepository extends JpaRepository<AssetFolderEntity, Long> {

    public void deleteByNumber(final Long number);

    public boolean existsByNameAndParentIsNull(final String name);

    public boolean existsByNameAndParentIsNullAndNumberNot(final String name, final Long number);

    public boolean existsByNameAndParentNumber(final String name, final Long parentNumber);

    public boolean existsByNameAndParentNumberAndNumberNot(final String name, final Long parentNumber,
            final Long number);

    public boolean existsByNumber(final Long number);

    public boolean existsByParentNumber(final Long parentNumber);

    public Optional<AssetFolderEntity> findByNumber(final Long number);

    @Query("SELECT COALESCE(MAX(f.number), 0) + 1 FROM AssetFolder f")
    public Long findNextNumber();

}
