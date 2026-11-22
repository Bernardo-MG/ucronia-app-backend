
package com.bernardomg.asset.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.asset.domain.exception.AssetFolderNotExistingException;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.test.configuration.factory.AssetFolderConstants;
import com.bernardomg.asset.usecase.service.DefaultAssetFolderService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultAssetFolderService - getPublicAssets")
class TestAssetFolderServiceGetPublicImages {

    @Mock
    private AssetFolderRepository     folderRepository;

    @Mock
    private AssetRepository           imageRepository;

    @InjectMocks
    private DefaultAssetFolderService service;

    @Test
    @DisplayName("When there is an existing folder, then the requested public image page is returned")
    void testGetPublicImages() {
        final Page<Asset> existing;
        final Page<Asset> result;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, true, true, sorting);

        given(folderRepository.exists(AssetFolderConstants.NUMBER)).willReturn(true);
        given(imageRepository.findAllPublicByFolder(AssetFolderConstants.NUMBER, pagination, sorting))
            .willReturn(existing);

        // WHEN
        result = service.getPublicAssets(AssetFolderConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(result)
            .as("When there is an existing folder, then the requested public image page is returned")
            .isEqualTo(existing);
    }

    @Test
    @DisplayName("When there is a missing folder, then an exception is thrown")
    void testGetPublicImages_NotExisting() {
        final ThrowingCallable action;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();
        given(folderRepository.exists(AssetFolderConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.getPublicAssets(AssetFolderConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a missing folder, then an exception is thrown")
            .isInstanceOf(AssetFolderNotExistingException.class);
    }

}
