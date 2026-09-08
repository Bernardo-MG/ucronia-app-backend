/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.image.TestApplication;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.data.annotation.ValidImage;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ImageRepository - find all")
class ITImageRepositoryFindAll {

    @Autowired
    private ImageRepository repository;

    @Test
    @DisplayName("When there are images, they are returned")
    @ValidImage
    void testFindAll() {
        final Page<Image> images;
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
            .containsExactly(Images.valid());
    }

    @Test
    @DisplayName("When there are no images, nothing is returned")
    void testFindAll_NoData() {
        final Page<Image> images;
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
