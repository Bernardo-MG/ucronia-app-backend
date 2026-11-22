
package com.bernardomg.asset.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.test.configuration.TestApplication;
import com.bernardomg.asset.test.configuration.data.annotation.ValidFileFolder;
import com.bernardomg.asset.test.configuration.data.annotation.ValidFileFolderTree;
import com.bernardomg.asset.test.configuration.factory.AssetFolderConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetFolderRepository - existsByNameAndParent")
class ITAssetFolderRepositoryExistsByNameAndParent {

    @Autowired
    private AssetFolderRepository repository;

    @Test
    @DisplayName("When there is a child folder with the name and parent, then it exists")
    @ValidFileFolderTree
    void testExistsByNameAndParent_Child() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(AssetFolderConstants.CHILD_NAME, AssetFolderConstants.NUMBER, null);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When the matching folder is excluded, then it doesn't exist")
    @ValidFileFolderTree
    void testExistsByNameAndParent_Excluded() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(AssetFolderConstants.CHILD_NAME, AssetFolderConstants.NUMBER,
            AssetFolderConstants.CHILD_NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is no matching folder, then it doesn't exist")
    void testExistsByNameAndParent_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(AssetFolderConstants.NAME, null, null);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is a root folder with the name, then it exists")
    @ValidFileFolder
    void testExistsByNameAndParent_Root() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(AssetFolderConstants.NAME, null, null);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When the matching root folder is excluded, then it doesn't exist")
    @ValidFileFolder
    void testExistsByNameAndParent_Root_Excluded() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(AssetFolderConstants.NAME, null, AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
