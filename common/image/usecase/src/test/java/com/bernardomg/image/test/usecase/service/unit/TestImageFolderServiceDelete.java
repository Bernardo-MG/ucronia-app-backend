
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

import com.bernardomg.image.domain.exception.ImageFolderNotEmptyException;
import com.bernardomg.image.domain.exception.ImageFolderNotExistingException;
import com.bernardomg.image.domain.model.ImageFolder;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.image.test.configuration.factory.ImageFolders;
import com.bernardomg.image.usecase.service.DefaultImageFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageFolderService - delete")
class TestImageFolderServiceDelete {

    @Mock
    private ImageFolderRepository     folderRepository;

    @Mock
    private ImageRepository           imageRepository;

    @InjectMocks
    private DefaultImageFolderService service;

    @Test
    @DisplayName("With an empty folder, the image folder is deleted and returned")
    void testDelete() {
        final ImageFolder deleted;

        // GIVEN
        given(folderRepository.findOne(ImageFolderConstants.NUMBER)).willReturn(Optional.of(ImageFolders.valid()));

        // WHEN
        deleted = service.delete(ImageFolderConstants.NUMBER);

        // THEN
        verify(folderRepository).delete(ImageFolderConstants.NUMBER);
        Assertions.assertThat(deleted)
            .isEqualTo(ImageFolders.valid());
    }

    @Test
    @DisplayName("With a missing image folder, an exception is thrown")
    void testDelete_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.findOne(ImageFolderConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.delete(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ImageFolderNotExistingException.class);
    }

    @Test
    @DisplayName("With child folders, an exception is thrown")
    void testDelete_WithChildren() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.findOne(ImageFolderConstants.NUMBER)).willReturn(Optional.of(ImageFolders.valid()));
        given(folderRepository.hasChildren(ImageFolderConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.delete(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ImageFolderNotEmptyException.class);
    }

    @Test
    @DisplayName("With images, an exception is thrown")
    void testDelete_WithImages() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.findOne(ImageFolderConstants.NUMBER)).willReturn(Optional.of(ImageFolders.valid()));
        given(imageRepository.hasImagesInFolder(ImageFolderConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.delete(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ImageFolderNotEmptyException.class);
    }

}
