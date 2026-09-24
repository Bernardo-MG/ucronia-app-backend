
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

    @Query("""
            SELECT CASE WHEN COUNT(i) > 0 THEN TRUE ELSE FALSE END
            FROM Image i
            WHERE i.name = :name
              AND ((:folderNumber IS NULL AND i.folder IS NULL) OR i.folder.number = :folderNumber)
            """)
    public boolean existsByNameAndFolder(@Param("name") final String name,
            @Param("folderNumber") final Long folderNumber);

    @Query("""
            SELECT CASE WHEN COUNT(i) > 0 THEN TRUE ELSE FALSE END
            FROM Image i
            WHERE i.name = :name
              AND i.number != :excludedNumber
              AND ((:folderNumber IS NULL AND i.folder IS NULL) OR i.folder.number = :folderNumber)
            """)
    public boolean existsByNameAndFolder(@Param("name") final String name,
            @Param("folderNumber") final Long folderNumber, @Param("excludedNumber") final long excludedNumber);

    public boolean existsByNumber(final long number);

    public Page<ImageEntity> findAllByFolderIsNull(final Pageable pageable);

    public Page<ImageEntity> findAllByFolderNumber(final long folderNumber, final Pageable pageable);

    public Optional<ImageEntity> findByNumber(final long number);

    @Query("SELECT COALESCE(MAX(i.number), 0) + 1 FROM Image i")
    public long findNextNumber();
}
