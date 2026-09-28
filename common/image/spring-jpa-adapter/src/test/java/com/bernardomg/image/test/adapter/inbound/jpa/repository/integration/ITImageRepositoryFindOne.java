
package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.image.TestApplication;
import com.bernardomg.image.test.configuration.data.annotation.PublicImage;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetRepository - find one")
class ITImageRepositoryFindOne {

    @Autowired
    private AssetRepository repository;

    @Test
    @DisplayName("With an image, it is returned")
    @PublicImage
    void testFindOne() {
        final Optional<Asset> image;

        // WHEN
        image = repository.findOne(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(image)
            .as("image")
            .contains(Images.publicAccess());
    }

    @Test
    @DisplayName("With no data, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Asset> image;

        // WHEN
        image = repository.findOne(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(image)
            .as("image")
            .isEmpty();
    }
}
