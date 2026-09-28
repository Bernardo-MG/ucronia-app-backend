
package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.image.TestApplication;
import com.bernardomg.image.test.configuration.data.annotation.PrivateImageInFolder;
import com.bernardomg.image.test.configuration.data.annotation.PublicImageInFolder;
import com.bernardomg.image.test.configuration.data.annotation.ValidImageFolderTree;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("Asset repository - find all by folder")
class ITImageRepositoryFindAllByFolder {

    @Autowired
    private AssetRepository repository;

    @Test
    @ValidImageFolderTree
    @PrivateImageInFolder
    @DisplayName("With a private image in the folder, it is returned")
    void testfindAllByFolder_Private() {
        final Page<Asset> result;

        // WHEN
        result = repository.findAllByFolder(ImageFolderConstants.NUMBER, new Pagination(1, 10), Sorting.unsorted());

        // THEN
        Assertions.assertThat(result.content())
            .containsExactly(Images.privateAccessInFolder());
    }

    @Test
    @ValidImageFolderTree
    @PublicImageInFolder
    @DisplayName("With a public image in the folder, it is returned")
    void testfindAllByFolder_Public() {
        final Page<Asset> result;

        // WHEN
        result = repository.findAllByFolder(ImageFolderConstants.NUMBER, new Pagination(1, 10), Sorting.unsorted());

        // THEN
        Assertions.assertThat(result.content())
            .containsExactly(Images.publicAccessInFolder());
    }

}
