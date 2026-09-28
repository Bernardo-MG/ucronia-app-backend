
package com.bernardomg.file.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.file.TestApplication;
import com.bernardomg.file.test.configuration.data.annotation.PrivateFileInFolder;
import com.bernardomg.file.test.configuration.data.annotation.PublicFileInFolder;
import com.bernardomg.file.test.configuration.data.annotation.ValidFileFolderTree;
import com.bernardomg.file.test.configuration.factory.FileFolderConstants;
import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("Asset repository - find all public by folder")
class ITFileRepositoryFindAllPublicByFolder {

    @Autowired
    private AssetRepository repository;

    @Test
    @ValidFileFolderTree
    @PrivateFileInFolder
    @DisplayName("With a private file in the folder, it is not returned")
    void testFindAllPublicByFolder_Private() {
        final Page<Asset> result;

        // WHEN
        result = repository.findAllPublicByFolder(FileFolderConstants.NUMBER, new Pagination(1, 10),
            Sorting.unsorted());

        // THEN
        Assertions.assertThat(result.content())
            .isEmpty();
    }

    @Test
    @ValidFileFolderTree
    @PublicFileInFolder
    @DisplayName("With a public file in the folder, it is returned")
    void testFindAllPublicByFolder_Public() {
        final Page<Asset> result;

        // WHEN
        result = repository.findAllPublicByFolder(FileFolderConstants.NUMBER, new Pagination(1, 10),
            Sorting.unsorted());

        // THEN
        Assertions.assertThat(result.content())
            .containsExactly(Files.publicAccessInFolder());
    }

}
