
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

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
import com.bernardomg.image.domain.exception.ImageAlreadyExistsException;
import com.bernardomg.image.domain.exception.ImageFolderNotExistingException;
import com.bernardomg.image.domain.exception.ImageNotExistingException;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.image.usecase.service.DefaultImageFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageFolderService - move image")
class TestImageFolderServiceMoveImage {

    @Mock
    private AssetFolderRepository     folderRepository;

    @Mock
    private AssetRepository           imageRepository;

    @InjectMocks
    private DefaultImageFolderService service;

    @Test
    @DisplayName("With an existing image and folder, the image is moved")
    void testMoveImage() {
        final Asset moved;

        // GIVEN
        given(folderRepository.exists(ImageFolderConstants.NUMBER)).willReturn(true);
        given(imageRepository.findOne(ImageFolderConstants.IMAGE_NUMBER))
            .willReturn(Optional.of(Images.publicAccess()));
        given(imageRepository.move(ImageFolderConstants.IMAGE_NUMBER, ImageFolderConstants.NUMBER))
            .willReturn(Images.publicAccess());

        // WHEN
        moved = service.moveImage(ImageFolderConstants.IMAGE_NUMBER, ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(moved)
            .isEqualTo(Images.publicAccess());
    }

    @Test
    @DisplayName("With a missing folder, an exception is thrown")
    void testMoveImage_MissingFolder() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(ImageFolderConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.moveImage(ImageFolderConstants.IMAGE_NUMBER, ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ImageFolderNotExistingException.class);
    }

    @Test
    @DisplayName("With a missing image, an exception is thrown")
    void testMoveImage_MissingImage() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(ImageFolderConstants.NUMBER)).willReturn(true);
        given(imageRepository.findOne(ImageFolderConstants.IMAGE_NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.moveImage(ImageFolderConstants.IMAGE_NUMBER, ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ImageNotExistingException.class);
    }

    @Test
    @DisplayName("With the same name in the target folder, an exception is thrown")
    void testMoveImage_NameConflict() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(ImageFolderConstants.NUMBER)).willReturn(true);
        given(imageRepository.findOne(ImageFolderConstants.IMAGE_NUMBER))
            .willReturn(Optional.of(Images.publicAccess()));
        given(imageRepository.existsByNameAndFolder(Images.publicAccess()
            .name(), ImageFolderConstants.NUMBER,
            Images.publicAccess()
                .number())).willReturn(true);

        // WHEN
        execution = () -> service.moveImage(ImageFolderConstants.IMAGE_NUMBER, ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ImageAlreadyExistsException.class);
    }

}
