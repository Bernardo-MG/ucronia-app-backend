
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.image.domain.model.ImageFolder;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.ImageFolders;
import com.bernardomg.image.usecase.service.DefaultImageFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageFolderService - get all")
class TestImageFolderServiceGetAll {

    @Mock
    private ImageFolderRepository     folderRepository;

    @Mock
    private ImageRepository           imageRepository;

    @InjectMocks
    private DefaultImageFolderService service;

    @Test
    @DisplayName("When reading all image folders, all image folders are returned")
    void testGetAll() {
        final Collection<ImageFolder> folders;

        // GIVEN
        given(folderRepository.findAll()).willReturn(List.of(ImageFolders.valid()));

        // WHEN
        folders = service.getAll();

        // THEN
        Assertions.assertThat(folders)
            .containsExactly(ImageFolders.valid());
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testGetAll_Empty() {
        final Collection<ImageFolder> folders;

        // GIVEN
        given(folderRepository.findAll()).willReturn(List.of());

        // WHEN
        folders = service.getAll();

        // THEN
        Assertions.assertThat(folders)
            .isEmpty();
    }

}
