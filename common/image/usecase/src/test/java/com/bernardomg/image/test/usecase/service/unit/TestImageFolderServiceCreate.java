
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
@DisplayName("ImageFolderService - create")
class TestImageFolderServiceCreate {

    @Mock
    private ImageFolderRepository     folderRepository;

    @Mock
    private ImageRepository           imageRepository;

    @InjectMocks
    private DefaultImageFolderService service;

    @Test
    @DisplayName("With an existing sibling name, an exception is thrown")
    void testCreate_ExistingName() {
        final ThrowingCallable execution;
        final ImageFolder      folder;

        // GIVEN
        folder = ImageFolders.toCreate();
        given(folderRepository.existsByNameAndParent(ImageFolderConstants.NAME, null, null)).willReturn(true);

        // WHEN
        execution = () -> service.create(folder);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ImageFolderAlreadyExistsException.class);
        verify(folderRepository, never()).save(folder);
    }

    @Test
    @DisplayName("With a missing parent, an exception is thrown")
    void testCreate_MissingParent() {
        final ThrowingCallable execution;
        final ImageFolder      folder;

        // GIVEN
        folder = ImageFolders.withParent();
        given(folderRepository.exists(ImageFolderConstants.PARENT_NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.create(folder);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ImageFolderNotExistingException.class);
    }

    @Test
    @DisplayName("With valid data, the image folder is persisted")
    void testCreate_PersistedData() {
        // GIVEN
        given(folderRepository.existsByNameAndParent(ImageFolderConstants.NAME, null, null)).willReturn(false);

        // WHEN
        service.create(ImageFolders.toCreate());

        // THEN
        verify(folderRepository).save(ImageFolders.toCreate());
    }

    @Test
    @DisplayName("With valid data, the created image folder is returned")
    void testCreate_ReturnedData() {
        final ImageFolder created;

        // GIVEN
        given(folderRepository.save(ImageFolders.toCreate())).willReturn(ImageFolders.valid());

        // WHEN
        created = service.create(ImageFolders.toCreate());

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(ImageFolders.valid());
    }

}
