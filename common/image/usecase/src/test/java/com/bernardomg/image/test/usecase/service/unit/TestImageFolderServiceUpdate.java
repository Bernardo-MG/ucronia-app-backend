
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.image.domain.exception.ImageFolderAlreadyExistsException;
import com.bernardomg.image.domain.exception.ImageFolderNotExistingException;
import com.bernardomg.image.domain.model.ImageFolder;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.image.test.configuration.factory.ImageFolders;
import com.bernardomg.image.usecase.service.DefaultImageFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageFolderService - update")
class TestImageFolderServiceUpdate {

    @Mock
    private ImageFolderRepository     folderRepository;

    @Mock
    private ImageRepository           imageRepository;

    @InjectMocks
    private DefaultImageFolderService service;

    @Test
    @DisplayName("With an existing sibling name, an exception is thrown")
    void testUpdate_ExistingName() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(ImageFolderConstants.NUMBER)).willReturn(true);
        given(folderRepository.findOne(ImageFolderConstants.NUMBER)).willReturn(Optional.of(ImageFolders.valid()));
        given(folderRepository.existsByNameAndParent(ImageFolderConstants.NAME, null, ImageFolderConstants.NUMBER))
            .willReturn(true);

        // WHEN
        execution = () -> service.update(ImageFolders.valid());

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ImageFolderAlreadyExistsException.class);
    }

    @Test
    @DisplayName("With a missing image folder, an exception is thrown")
    void testUpdate_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(ImageFolderConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(ImageFolders.valid());

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ImageFolderNotExistingException.class);
    }

    @Test
    @DisplayName("With valid data, the image folder is persisted")
    void testUpdate_PersistedData() {
        // GIVEN
        given(folderRepository.exists(ImageFolderConstants.NUMBER)).willReturn(true);
        given(folderRepository.findOne(ImageFolderConstants.NUMBER)).willReturn(Optional.of(ImageFolders.valid()));

        // WHEN
        service.update(ImageFolders.valid());

        // THEN
        verify(folderRepository).save(ImageFolders.valid());
    }

    @Test
    @DisplayName("With valid data, the updated image folder is returned")
    void testUpdate_ReturnedData() {
        final ImageFolder updated;

        // GIVEN
        given(folderRepository.exists(ImageFolderConstants.NUMBER)).willReturn(true);
        given(folderRepository.findOne(ImageFolderConstants.NUMBER)).willReturn(Optional.of(ImageFolders.valid()));
        given(folderRepository.save(ImageFolders.valid())).willReturn(ImageFolders.valid());

        // WHEN
        updated = service.update(ImageFolders.valid());

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(ImageFolders.valid());
    }

}
