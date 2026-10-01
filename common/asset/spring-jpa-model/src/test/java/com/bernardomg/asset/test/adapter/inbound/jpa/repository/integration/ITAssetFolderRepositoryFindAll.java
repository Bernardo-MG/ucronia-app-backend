
package com.bernardomg.asset.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.test.configuration.TestApplication;
import com.bernardomg.asset.test.configuration.data.annotation.ValidFileFolder;
import com.bernardomg.asset.test.configuration.factory.AssetFolders;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetFolderRepository - find all")
class ITAssetFolderRepositoryFindAll {

    @Autowired
    private AssetFolderRepository repository;

    @Test
    @DisplayName("When there are file folders, they are returned")
    @ValidFileFolder
    void testFindAll() {
        final Collection<AssetFolder> folders;

        // WHEN
        folders = repository.findAll();

        // THEN
        Assertions.assertThat(folders)
            .as("file folders")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(AssetFolders.valid());
    }

    @Test
    @DisplayName("When there are no file folders, nothing is returned")
    void testFindAll_NoData() {
        final Collection<AssetFolder> folders;

        // WHEN
        folders = repository.findAll();

        // THEN
        Assertions.assertThat(folders)
            .as("file folders")
            .isEmpty();
    }

}
