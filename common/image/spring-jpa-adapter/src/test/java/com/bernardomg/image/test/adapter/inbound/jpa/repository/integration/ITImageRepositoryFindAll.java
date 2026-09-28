
package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.image.TestApplication;
import com.bernardomg.image.test.configuration.data.annotation.PublicImage;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetRepository - find all")
class ITImageRepositoryFindAll {

    @Autowired
    private AssetRepository repository;

    @Test
    @DisplayName("When there are images, they are returned")
    @PublicImage
    void testFindAll() {
        final Page<Asset> images;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        images = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(images)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("images")
            .containsExactly(Images.publicAccess());
    }

    @Test
    @DisplayName("When there are no images, nothing is returned")
    void testFindAll_NoData() {
        final Page<Asset> images;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        images = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(images)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("images")
            .isEmpty();
    }
}
