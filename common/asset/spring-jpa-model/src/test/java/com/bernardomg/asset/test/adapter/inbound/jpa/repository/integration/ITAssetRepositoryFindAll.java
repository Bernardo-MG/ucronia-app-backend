
package com.bernardomg.asset.test.adapter.inbound.jpa.repository.integration;

import static com.bernardomg.asset.domain.model.AssetType.FILE;
import static com.bernardomg.asset.domain.model.AssetType.IMAGE;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.test.configuration.TestApplication;
import com.bernardomg.asset.test.configuration.data.annotation.PublicFile;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetRepository - find all")
class ITAssetRepositoryFindAll {

    @Autowired
    private AssetRepository repository;

    @Test
    @DisplayName("When there are files, they are returned")
    @PublicFile
    void testFindAll() {
        final Page<Asset> files;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        files = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(files)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("files")
            .containsExactly(Assets.publicAccess());
    }

    @Test
    @DisplayName("When filtering by file type, files are returned")
    @PublicFile
    void testFindAll_FileType() {
        final Page<Asset> files;

        // WHEN
        files = repository.findAll(FILE, new Pagination(1, 20), Sorting.unsorted());

        // THEN
        Assertions.assertThat(files.content())
            .containsExactly(Assets.publicAccess());
    }

    @Test
    @DisplayName("When filtering by image type, files are not returned")
    @PublicFile
    void testFindAll_ImageType() {
        final Page<Asset> images;

        // WHEN
        images = repository.findAll(IMAGE, new Pagination(1, 20), Sorting.unsorted());

        // THEN
        Assertions.assertThat(images.content())
            .isEmpty();
    }

    @Test
    @DisplayName("When there are no files, nothing is returned")
    void testFindAll_NoData() {
        final Page<Asset> files;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        files = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(files)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("files")
            .isEmpty();
    }
}
