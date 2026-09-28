
package com.bernardomg.image.test.usecase.service.unit;

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

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.image.domain.exception.ImageFolderNotExistingException;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.image.usecase.service.DefaultImageFolderService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageFolderService - get images")
class TestImageFolderServiceGetImages {

    @Mock
    private AssetFolderRepository     folderRepository;

    @Mock
    private AssetRepository           imageRepository;

    @InjectMocks
    private DefaultImageFolderService service;

    @Test
    @DisplayName("With an existing folder, the requested image page is returned")
    void testGetImages() {
        final Page<Asset> result;
        final Page<Asset> existing;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, true, true, sorting);

        given(folderRepository.exists(ImageFolderConstants.NUMBER)).willReturn(true);
        given(imageRepository.findAllByFolder(ImageFolderConstants.NUMBER, pagination, sorting)).willReturn(existing);

        // WHEN
        result = service.getImages(ImageFolderConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(result)
            .isEqualTo(existing);
    }

    @Test
    @DisplayName("With a missing folder, an exception is thrown")
    void testGetImages_NotExisting() {
        final ThrowingCallable execution;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();
        given(folderRepository.exists(ImageFolderConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.getImages(ImageFolderConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ImageFolderNotExistingException.class);
    }

}
