
package com.bernardomg.asset.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.usecase.service.DefaultAssetFolderService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("Asset folder service - get public root images")
class TestAssetFolderServiceGetPublicRootImages {

    @Mock
    private AssetFolderRepository     folderRepository;

    @Mock
    private AssetRepository           imageRepository;

    @InjectMocks
    private DefaultAssetFolderService service;

    @Test
    @DisplayName("When reading public root images, the requested page is returned")
    void testGetPublicRootImages() {
        final Page<Asset> existing;
        final Page<Asset> result;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, true, true, sorting);
        given(imageRepository.findAllPublicByFolder(null, pagination, sorting)).willReturn(existing);

        // WHEN
        result = service.getPublicRootAssets(pagination, sorting);

        // THEN
        Assertions.assertThat(result)
            .isEqualTo(existing);
    }

    @Test
    @DisplayName("When reading public root images with no data, the requested page is empty")
    void testGetPublicRootImages_NoData() {
        final Page<Asset> existing;
        final Page<Asset> result;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, true, true, sorting);
        given(imageRepository.findAllPublicByFolder(null, pagination, sorting)).willReturn(existing);

        // WHEN
        result = service.getPublicRootAssets(pagination, sorting);

        // THEN
        Assertions.assertThat(result.content())
            .isEmpty();
    }

}
