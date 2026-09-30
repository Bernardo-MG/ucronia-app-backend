
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
@DisplayName("AssetFolderRepository - has children")
class ITAssetFolderRepositoryHasChildren {

    @Autowired
    private AssetFolderRepository repository;

    @Test
    @DisplayName("With child folders, it has children")
    @ValidFileFolderTree
    void testHasChildren() {
        final boolean hasChildren;

        // WHEN
        hasChildren = repository.hasChildren(AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(hasChildren)
            .as("has children")
            .isTrue();
    }

    @Test
    @DisplayName("Without child folders, it doesn't have children")
    @ValidFileFolder
    void testHasChildren_NoChildren() {
        final boolean hasChildren;

        // WHEN
        hasChildren = repository.hasChildren(AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(hasChildren)
            .as("has children")
            .isFalse();
    }

}
