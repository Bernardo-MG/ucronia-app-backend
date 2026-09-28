
package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.adapter.inbound.jpa.repository.AssetSpringRepository;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.image.TestApplication;
import com.bernardomg.image.test.configuration.data.annotation.PublicImage;
import com.bernardomg.image.test.configuration.factory.ImageEntities;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetRepository - save")
class ITImageRepositorySave {

    @Autowired
    private AssetRepository       repository;

    @Autowired
    private AssetSpringRepository springRepository;

    @Test
    @DisplayName("When the image name is changed, it is updated")
    @PublicImage
    void testSave_Existing_ChangeName_Persisted() {
        final Asset image;

        // GIVEN
        image = Images.nameChange();

        // WHEN
        repository.save(image);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("images")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(ImageEntities.nameChange());
    }

    @Test
    @DisplayName("When the image name is changed, it is returned")
    @PublicImage
    void testSave_Existing_ChangeName_Returned() {
        final Asset image;
        final Asset saved;

        // GIVEN
        image = Images.nameChange();

        // WHEN
        saved = repository.save(image);

        // THEN
        Assertions.assertThat(saved)
            .as("image")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Images.nameChange());
    }

    @Test
    @DisplayName("When saving, an image is persisted")
    void testSave_Persisted() {
        // WHEN
        repository.save(Images.publicAccess());

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("images")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(ImageEntities.publicAccess());
    }

    @Test
    @DisplayName("When saving, the persisted image is returned")
    void testSave_Returned() {
        final Asset saved;

        // WHEN
        saved = repository.save(Images.publicAccess());

        // THEN
        Assertions.assertThat(saved)
            .as("image")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Images.publicAccess());
    }
}
