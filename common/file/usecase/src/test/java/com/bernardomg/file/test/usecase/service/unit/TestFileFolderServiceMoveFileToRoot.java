
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
import com.bernardomg.file.domain.exception.FileNotExistingException;
import com.bernardomg.file.domain.model.File;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.domain.repository.FileRepository;
import com.bernardomg.file.test.configuration.factory.FileFolderConstants;
import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.file.usecase.service.DefaultFileFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("FileFolderService - move file to root")
class TestFileFolderServiceMoveFileToRoot {

    @Mock
    private FileRepository           fileRepository;

    @Mock
    private FileFolderRepository     folderRepository;

    @InjectMocks
    private DefaultFileFolderService service;

    @Test
    @DisplayName("With an existing file, the file is moved to root")
    void testMoveFileToRoot() {
        final File moved;

        // GIVEN
        given(fileRepository.findOne(FileFolderConstants.FILE_NUMBER)).willReturn(Optional.of(Files.publicAccess()));
        given(fileRepository.move(FileFolderConstants.FILE_NUMBER, null)).willReturn(Files.publicAccess());

        // WHEN
        moved = service.moveFileToRoot(FileFolderConstants.FILE_NUMBER);

        // THEN
        Assertions.assertThat(moved)
            .isEqualTo(Files.publicAccess());
    }

    @Test
    @DisplayName("With a missing file, an exception is thrown")
    void testMoveFileToRoot_MissingFile() {
        final ThrowingCallable execution;

        // GIVEN
        given(fileRepository.findOne(FileFolderConstants.FILE_NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.moveFileToRoot(FileFolderConstants.FILE_NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileNotExistingException.class);
    }

    @Test
    @DisplayName("With the same name in the root folder, an exception is thrown")
    void testMoveFileToRoot_NameConflict() {
        final ThrowingCallable execution;

        // GIVEN
        given(fileRepository.findOne(FileFolderConstants.FILE_NUMBER)).willReturn(Optional.of(Files.publicAccess()));
        given(fileRepository.existsByNameAndFolder(Files.publicAccess()
            .name(), null,
            Files.publicAccess()
                .number())).willReturn(true);

        // WHEN
        execution = () -> service.moveFileToRoot(FileFolderConstants.FILE_NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileAlreadyExistsException.class);
    }

}
