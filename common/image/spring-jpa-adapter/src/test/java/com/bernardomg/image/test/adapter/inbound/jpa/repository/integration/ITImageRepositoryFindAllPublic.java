
package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.image.TestApplication;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.data.annotation.PrivateImage;
import com.bernardomg.image.test.configuration.data.annotation.PublicImage;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("Image repository - find all public")
class ITImageRepositoryFindAllPublic {

    @Autowired
    private ImageRepository repository;

    @Test
    @PrivateImage
    @DisplayName("With a private image, it is not returned")
    void testFindAllPublic_Private() {
        final Page<Image> result;

        // WHEN
        result = repository.findAllPublic(new Pagination(1, 10), Sorting.unsorted());

        // THEN
        Assertions.assertThat(result.content())
            .isEmpty();
    }

    @Test
    @PublicImage
    @DisplayName("With a public image, it is returned")
    void testFindAllPublic_Public() {
        final Page<Image> result;

        // WHEN
        result = repository.findAllPublic(new Pagination(1, 10), Sorting.unsorted());

        // THEN
        Assertions.assertThat(result.content())
            .containsExactly(Images.publicAccess());
    }

}
