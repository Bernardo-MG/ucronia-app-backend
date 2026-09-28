
package com.bernardomg.file.test.usecase.service.unit;

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

import com.bernardomg.file.domain.exception.FileAlreadyExistsException;
import com.bernardomg.file.domain.exception.FileFolderNotExistingException;
import com.bernardomg.file.domain.exception.FileNotExistingException;
import com.bernardomg.file.domain.model.File;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.domain.repository.FileRepository;
import com.bernardomg.file.test.configuration.factory.FileFolderConstants;
import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.file.usecase.service.DefaultFileFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("FileFolderService - move file")
class TestFileFolderServiceMoveFile {

    @Mock
    private FileRepository           fileRepository;

    @Mock
    private FileFolderRepository     folderRepository;

    @InjectMocks
    private DefaultFileFolderService service;

    @Test
    @DisplayName("With an existing file and folder, the file is moved")
    void testMoveFile() {
        final File moved;

        // GIVEN
        given(folderRepository.exists(FileFolderConstants.NUMBER)).willReturn(true);
        given(fileRepository.findOne(FileFolderConstants.FILE_NUMBER)).willReturn(Optional.of(Files.publicAccess()));
        given(fileRepository.move(FileFolderConstants.FILE_NUMBER, FileFolderConstants.NUMBER))
            .willReturn(Files.publicAccess());

        // WHEN
        moved = service.moveFile(FileFolderConstants.FILE_NUMBER, FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(moved)
            .isEqualTo(Files.publicAccess());
    }

    @Test
    @DisplayName("With a missing file, an exception is thrown")
    void testMoveFile_MissingFile() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(FileFolderConstants.NUMBER)).willReturn(true);
        given(fileRepository.findOne(FileFolderConstants.FILE_NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.moveFile(FileFolderConstants.FILE_NUMBER, FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileNotExistingException.class);
    }

    @Test
    @DisplayName("With a missing folder, an exception is thrown")
    void testMoveFile_MissingFolder() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(FileFolderConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.moveFile(FileFolderConstants.FILE_NUMBER, FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileFolderNotExistingException.class);
    }

    @Test
    @DisplayName("With the same name in the target folder, an exception is thrown")
    void testMoveFile_NameConflict() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(FileFolderConstants.NUMBER)).willReturn(true);
        given(fileRepository.findOne(FileFolderConstants.FILE_NUMBER)).willReturn(Optional.of(Files.publicAccess()));
        given(fileRepository.existsByNameAndFolder(Files.publicAccess()
            .name(), FileFolderConstants.NUMBER,
            Files.publicAccess()
                .number())).willReturn(true);

        // WHEN
        execution = () -> service.moveFile(FileFolderConstants.FILE_NUMBER, FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileAlreadyExistsException.class);
    }

}
