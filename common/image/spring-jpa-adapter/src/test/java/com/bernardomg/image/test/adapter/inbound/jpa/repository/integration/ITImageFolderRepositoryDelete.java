
package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.adapter.inbound.jpa.repository.AssetFolderSpringRepository;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.image.TestApplication;
import com.bernardomg.image.test.configuration.data.annotation.ValidImageFolder;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetFolderRepository - delete")
class ITImageFolderRepositoryDelete {

    @Autowired
    private AssetFolderRepository       repository;

    @Autowired
    private AssetFolderSpringRepository springRepository;

    @Test
    @DisplayName("With an image folder, it is deleted")
    @ValidImageFolder
    void testDelete() {
        // WHEN
        repository.delete(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("image folders")
            .isZero();
    }

    @Test
    @DisplayName("With no data, nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("image folders")
            .isZero();
    }

}
