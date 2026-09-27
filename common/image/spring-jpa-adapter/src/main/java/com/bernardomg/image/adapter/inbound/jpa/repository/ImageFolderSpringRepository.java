
package com.bernardomg.image.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.image.adapter.inbound.jpa.model.ImageFolderEntity;

public interface ImageFolderSpringRepository extends JpaRepository<ImageFolderEntity, Long> {

    public void deleteByNumber(final Long number);

    public boolean existsByNameAndParentIsNull(final String name);

    public boolean existsByNameAndParentIsNullAndNumberNot(final String name, final Long number);

    public boolean existsByNameAndParentNumber(final String name, final Long parentNumber);

    public boolean existsByNameAndParentNumberAndNumberNot(final String name, final Long parentNumber,
            final Long number);

    public boolean existsByNumber(final Long number);

    public boolean existsByParentNumber(final Long parentNumber);

    public Optional<ImageFolderEntity> findByNumber(final Long number);

    @Query("SELECT COALESCE(MAX(f.number), 0) + 1 FROM ImageFolder f")
    public Long findNextNumber();

}
