
package com.bernardomg.file.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.file.adapter.inbound.jpa.model.FileFolderEntity;

public interface FileFolderSpringRepository extends JpaRepository<FileFolderEntity, Long> {

    public void deleteByNumber(final Long number);

    public boolean existsByNameAndParentIsNull(final String name);

    public boolean existsByNameAndParentIsNullAndNumberNot(final String name, final Long number);

    public boolean existsByNameAndParentNumber(final String name, final Long parentNumber);

    public boolean existsByNameAndParentNumberAndNumberNot(final String name, final Long parentNumber,
            final Long number);

    public boolean existsByNumber(final Long number);

    public boolean existsByParentNumber(final Long parentNumber);

    public Optional<FileFolderEntity> findByNumber(final Long number);

    @Query("SELECT COALESCE(MAX(f.number), 0) + 1 FROM FileFolder f")
    public Long findNextNumber();

}
