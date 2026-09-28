
package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.image.TestApplication;
import com.bernardomg.image.test.configuration.data.annotation.PublicImage;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetRepository - exists by name")
class ITImageRepositoryExistsByName {

    @Autowired
    private AssetRepository repository;

    @Test
    @DisplayName("With an image with the name, it exists")
    @PublicImage
    void testExistsByName() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndFolder(ImageConstants.NAME, null);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("With no data, it doesn't exist")
    void testExistsByName_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndFolder(ImageConstants.NAME, null);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }
}
