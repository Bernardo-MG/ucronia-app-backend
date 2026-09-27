
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.usecase.service.DefaultImageFolderService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image folder service - get public root images")
class TestImageFolderServiceGetPublicRootImages {

    @Mock
    private ImageFolderRepository     folderRepository;

    @Mock
    private ImageRepository           imageRepository;

    @InjectMocks
    private DefaultImageFolderService service;

    @Test
    @DisplayName("When reading public root images, the requested page is returned")
    void testGetPublicRootImages() {
        final Page<Image> existing;
        final Page<Image> result;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, true, true, sorting);
        given(imageRepository.findAllPublicByFolder(null, pagination, sorting)).willReturn(existing);

        // WHEN
        result = service.getPublicRootImages(pagination, sorting);

        // THEN
        Assertions.assertThat(result)
            .isEqualTo(existing);
    }

    @Test
    @DisplayName("When reading public root images with no data, the requested page is empty")
    void testGetPublicRootImages_NoData() {
        final Page<Image> existing;
        final Page<Image> result;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, true, true, sorting);
        given(imageRepository.findAllPublicByFolder(null, pagination, sorting)).willReturn(existing);

        // WHEN
        result = service.getPublicRootImages(pagination, sorting);

        // THEN
        Assertions.assertThat(result.content())
            .isEmpty();
    }

}
