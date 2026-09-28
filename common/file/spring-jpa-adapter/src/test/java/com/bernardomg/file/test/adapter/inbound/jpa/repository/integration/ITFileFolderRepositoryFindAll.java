
package com.bernardomg.file.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.file.TestApplication;
import com.bernardomg.file.domain.model.FileFolder;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.test.configuration.data.annotation.ValidFileFolder;
import com.bernardomg.file.test.configuration.factory.FileFolders;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FileFolderRepository - find all")
class ITFileFolderRepositoryFindAll {

    @Autowired
    private FileFolderRepository repository;

    @Test
    @DisplayName("When there are file folders, they are returned")
    @ValidFileFolder
    void testFindAll() {
        final Collection<FileFolder> folders;

        // WHEN
        folders = repository.findAll();

        // THEN
        Assertions.assertThat(folders)
            .as("file folders")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(FileFolders.valid());
    }

    @Test
    @DisplayName("When there are no file folders, nothing is returned")
    void testFindAll_NoData() {
        final Collection<FileFolder> folders;

        // WHEN
        folders = repository.findAll();

        // THEN
        Assertions.assertThat(folders)
            .as("file folders")
            .isEmpty();
    }

}
