
package com.bernardomg.file.test.usecase.service.unit;

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

import com.bernardomg.file.domain.exception.FileFolderAlreadyExistsException;
import com.bernardomg.file.domain.exception.FileFolderNotExistingException;
import com.bernardomg.file.domain.model.FileFolder;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.domain.repository.FileRepository;
import com.bernardomg.file.test.configuration.factory.FileFolderConstants;
import com.bernardomg.file.test.configuration.factory.FileFolders;
import com.bernardomg.file.usecase.service.DefaultFileFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("FileFolderService - create")
class TestFileFolderServiceCreate {

    @Mock
    private FileRepository           fileRepository;

    @Mock
    private FileFolderRepository     folderRepository;

    @InjectMocks
    private DefaultFileFolderService service;

    @Test
    @DisplayName("With an existing sibling name, an exception is thrown")
    void testCreate_ExistingName() {
        final ThrowingCallable execution;
        final FileFolder       folder;

        // GIVEN
        folder = FileFolders.toCreate();
        given(folderRepository.existsByNameAndParent(FileFolderConstants.NAME, null, null)).willReturn(true);

        // WHEN
        execution = () -> service.create(folder);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileFolderAlreadyExistsException.class);
        verify(folderRepository, never()).save(folder);
    }

    @Test
    @DisplayName("With a missing parent, an exception is thrown")
    void testCreate_MissingParent() {
        final ThrowingCallable execution;
        final FileFolder       folder;

        // GIVEN
        folder = FileFolders.withParent();
        given(folderRepository.exists(FileFolderConstants.PARENT_NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.create(folder);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileFolderNotExistingException.class);
    }

    @Test
    @DisplayName("With valid data, the file folder is persisted")
    void testCreate_PersistedData() {
        // GIVEN
        given(folderRepository.existsByNameAndParent(FileFolderConstants.NAME, null, null)).willReturn(false);

        // WHEN
        service.create(FileFolders.toCreate());

        // THEN
        verify(folderRepository).save(FileFolders.toCreate());
    }

    @Test
    @DisplayName("With valid data, the created file folder is returned")
    void testCreate_ReturnedData() {
        final FileFolder created;

        // GIVEN
        given(folderRepository.save(FileFolders.toCreate())).willReturn(FileFolders.valid());

        // WHEN
        created = service.create(FileFolders.toCreate());

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(FileFolders.valid());
    }

}
