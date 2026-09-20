
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.image.domain.exception.ImageNotExistingException;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.image.usecase.service.DefaultImageFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageFolderService - move image to root")
class TestImageFolderServiceMoveImageToRoot {

    @Mock
    private ImageFolderRepository     folderRepository;

    @Mock
    private ImageRepository           imageRepository;

    @InjectMocks
    private DefaultImageFolderService service;

    @Test
    @DisplayName("With an existing image, the image is moved to root")
    void testMoveImageToRoot() {
        final Image moved;

        // GIVEN
        given(imageRepository.exists(ImageFolderConstants.IMAGE_NUMBER)).willReturn(true);
        given(imageRepository.move(ImageFolderConstants.IMAGE_NUMBER, null)).willReturn(Images.valid());

        // WHEN
        moved = service.moveImageToRoot(ImageFolderConstants.IMAGE_NUMBER);

        // THEN
        Assertions.assertThat(moved)
            .isEqualTo(Images.valid());
    }

    @Test
    @DisplayName("With a missing image, an exception is thrown")
    void testMoveImageToRoot_MissingImage() {
        final ThrowingCallable execution;

        // GIVEN
        given(imageRepository.exists(ImageFolderConstants.IMAGE_NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.moveImageToRoot(ImageFolderConstants.IMAGE_NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ImageNotExistingException.class);
    }

}
