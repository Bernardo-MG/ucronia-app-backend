
package com.bernardomg.file.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.file.TestApplication;
import com.bernardomg.file.adapter.inbound.jpa.repository.FileFolderSpringRepository;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.test.configuration.data.annotation.ValidFileFolder;
import com.bernardomg.file.test.configuration.factory.FileFolderConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FileFolderRepository - delete")
class ITFileFolderRepositoryDelete {

    @Autowired
    private FileFolderRepository       repository;

    @Autowired
    private FileFolderSpringRepository springRepository;

    @Test
    @DisplayName("With an file folder, it is deleted")
    @ValidFileFolder
    void testDelete() {
        // WHEN
        repository.delete(FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("file folders")
            .isZero();
    }

    @Test
    @DisplayName("With no data, nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("file folders")
            .isZero();
    }

}
