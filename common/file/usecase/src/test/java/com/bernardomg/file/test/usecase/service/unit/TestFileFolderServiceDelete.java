
package com.bernardomg.file.test.usecase.service.unit;

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

import com.bernardomg.file.domain.exception.FileFolderNotEmptyException;
import com.bernardomg.file.domain.exception.FileFolderNotExistingException;
import com.bernardomg.file.domain.model.FileFolder;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.domain.repository.FileRepository;
import com.bernardomg.file.test.configuration.factory.FileFolderConstants;
import com.bernardomg.file.test.configuration.factory.FileFolders;
import com.bernardomg.file.usecase.service.DefaultFileFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("FileFolderService - delete")
class TestFileFolderServiceDelete {

    @Mock
    private FileRepository           fileRepository;

    @Mock
    private FileFolderRepository     folderRepository;

    @InjectMocks
    private DefaultFileFolderService service;

    @Test
    @DisplayName("With an empty folder, the file folder is deleted and returned")
    void testDelete() {
        final FileFolder deleted;

        // GIVEN
        given(folderRepository.findOne(FileFolderConstants.NUMBER)).willReturn(Optional.of(FileFolders.valid()));

        // WHEN
        deleted = service.delete(FileFolderConstants.NUMBER);

        // THEN
        verify(folderRepository).delete(FileFolderConstants.NUMBER);
        Assertions.assertThat(deleted)
            .isEqualTo(FileFolders.valid());
    }

    @Test
    @DisplayName("With a missing file folder, an exception is thrown")
    void testDelete_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.findOne(FileFolderConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.delete(FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileFolderNotExistingException.class);
    }

    @Test
    @DisplayName("With child folders, an exception is thrown")
    void testDelete_WithChildren() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.findOne(FileFolderConstants.NUMBER)).willReturn(Optional.of(FileFolders.valid()));
        given(folderRepository.hasChildren(FileFolderConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.delete(FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileFolderNotEmptyException.class);
    }

    @Test
    @DisplayName("With files, an exception is thrown")
    void testDelete_WithFiles() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.findOne(FileFolderConstants.NUMBER)).willReturn(Optional.of(FileFolders.valid()));
        given(fileRepository.hasFilesInFolder(FileFolderConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.delete(FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileFolderNotEmptyException.class);
    }

}
