
package com.bernardomg.file.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.file.TestApplication;
import com.bernardomg.file.domain.model.FileFolder;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.test.configuration.data.annotation.ValidFileFolder;
import com.bernardomg.file.test.configuration.factory.FileFolderConstants;
import com.bernardomg.file.test.configuration.factory.FileFolders;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FileFolderRepository - find one")
class ITFileFolderRepositoryFindOne {

    @Autowired
    private FileFolderRepository repository;

    @Test
    @DisplayName("With an file folder, it is returned")
    @ValidFileFolder
    void testFindOne() {
        final Optional<FileFolder> folder;

        // WHEN
        folder = repository.findOne(FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(folder)
            .as("file folder")
            .get()
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(FileFolders.valid());
    }

    @Test
    @DisplayName("With no data, nothing is returned")
    void testFindOne_NoData() {
        final Optional<FileFolder> folder;

        // WHEN
        folder = repository.findOne(FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(folder)
            .as("file folder")
            .isEmpty();
    }

}
