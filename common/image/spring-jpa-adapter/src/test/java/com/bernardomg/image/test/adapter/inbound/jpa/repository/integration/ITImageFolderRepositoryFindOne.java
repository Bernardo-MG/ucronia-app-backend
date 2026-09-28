
package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.image.TestApplication;
import com.bernardomg.image.test.configuration.data.annotation.ValidImageFolder;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.image.test.configuration.factory.ImageFolders;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetFolderRepository - find one")
class ITImageFolderRepositoryFindOne {

    @Autowired
    private AssetFolderRepository repository;

    @Test
    @DisplayName("With an image folder, it is returned")
    @ValidImageFolder
    void testFindOne() {
        final Optional<AssetFolder> folder;

        // WHEN
        folder = repository.findOne(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(folder)
            .as("image folder")
            .get()
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(ImageFolders.valid());
    }

    @Test
    @DisplayName("With no data, nothing is returned")
    void testFindOne_NoData() {
        final Optional<AssetFolder> folder;

        // WHEN
        folder = repository.findOne(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(folder)
            .as("image folder")
            .isEmpty();
    }

}
