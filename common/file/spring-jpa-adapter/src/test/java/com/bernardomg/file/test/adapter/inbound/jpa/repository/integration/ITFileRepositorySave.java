
package com.bernardomg.file.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.file.TestApplication;
import com.bernardomg.file.adapter.inbound.jpa.repository.FileSpringRepository;
import com.bernardomg.file.domain.model.File;
import com.bernardomg.file.domain.repository.FileRepository;
import com.bernardomg.file.test.configuration.data.annotation.PublicFile;
import com.bernardomg.file.test.configuration.factory.FileEntities;
import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FileRepository - save")
class ITFileRepositorySave {

    @Autowired
    private FileRepository       repository;

    @Autowired
    private FileSpringRepository springRepository;

    @Test
    @DisplayName("When the file name is changed, it is updated")
    @PublicFile
    void testSave_Existing_ChangeName_Persisted() {
        final File file;

        // GIVEN
        file = Files.nameChange();

        // WHEN
        repository.save(file);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("files")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(FileEntities.nameChange());
    }

    @Test
    @DisplayName("When the file name is changed, it is returned")
    @PublicFile
    void testSave_Existing_ChangeName_Returned() {
        final File file;
        final File saved;

        // GIVEN
        file = Files.nameChange();

        // WHEN
        saved = repository.save(file);

        // THEN
        Assertions.assertThat(saved)
            .as("file")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Files.nameChange());
    }

    @Test
    @DisplayName("When saving, an file is persisted")
    void testSave_Persisted() {
        // WHEN
        repository.save(Files.publicAccess());

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("files")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(FileEntities.publicAccess());
    }

    @Test
    @DisplayName("When saving, the persisted file is returned")
    void testSave_Returned() {
        final File saved;

        // WHEN
        saved = repository.save(Files.publicAccess());

        // THEN
        Assertions.assertThat(saved)
            .as("file")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Files.publicAccess());
    }
}
