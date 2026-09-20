
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

import com.bernardomg.image.domain.exception.ImageFolderNotExistingException;
import com.bernardomg.image.domain.model.ImageFolder;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.image.test.configuration.factory.ImageFolders;
import com.bernardomg.image.usecase.service.DefaultImageFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageFolderService - get one")
class TestImageFolderServiceGetOne {

    @Mock
    private ImageFolderRepository     folderRepository;

    @Mock
    private ImageRepository           imageRepository;

    @InjectMocks
    private DefaultImageFolderService service;

    @Test
    @DisplayName("When the image folder exists, it is returned")
    void testGetOne() {
        final ImageFolder folder;

        // GIVEN
        given(folderRepository.findOne(ImageFolderConstants.NUMBER)).willReturn(Optional.of(ImageFolders.valid()));

        // WHEN
        folder = service.getOne(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(folder)
            .isEqualTo(ImageFolders.valid());
    }

    @Test
    @DisplayName("When the image folder doesn't exist, an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.findOne(ImageFolderConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOne(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ImageFolderNotExistingException.class);
    }

}
