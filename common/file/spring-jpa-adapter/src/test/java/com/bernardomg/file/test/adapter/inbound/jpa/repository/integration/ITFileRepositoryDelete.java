
package com.bernardomg.file.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.adapter.inbound.jpa.repository.AssetSpringRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.file.TestApplication;
import com.bernardomg.file.test.configuration.data.annotation.PublicFile;
import com.bernardomg.file.test.configuration.factory.FileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetRepository - delete")
class ITFileRepositoryDelete {

    @Autowired
    private AssetRepository       repository;

    @Autowired
    private AssetSpringRepository springRepository;

    @Test
    @DisplayName("With an file, it is deleted")
    @PublicFile
    void testDelete() {
        // WHEN
        repository.delete(FileConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("files")
            .isZero();
    }

    @Test
    @DisplayName("With no data, nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(FileConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("files")
            .isZero();
    }
}
