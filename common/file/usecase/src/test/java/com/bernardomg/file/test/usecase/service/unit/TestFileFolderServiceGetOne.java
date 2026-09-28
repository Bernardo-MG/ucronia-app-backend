
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

import com.bernardomg.file.domain.exception.FileFolderNotExistingException;
import com.bernardomg.file.domain.model.FileFolder;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.domain.repository.FileRepository;
import com.bernardomg.file.test.configuration.factory.FileFolderConstants;
import com.bernardomg.file.test.configuration.factory.FileFolders;
import com.bernardomg.file.usecase.service.DefaultFileFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("FileFolderService - get one")
class TestFileFolderServiceGetOne {

    @Mock
    private FileRepository           fileRepository;

    @Mock
    private FileFolderRepository     folderRepository;

    @InjectMocks
    private DefaultFileFolderService service;

    @Test
    @DisplayName("When the file folder exists, it is returned")
    void testGetOne() {
        final FileFolder folder;

        // GIVEN
        given(folderRepository.findOne(FileFolderConstants.NUMBER)).willReturn(Optional.of(FileFolders.valid()));

        // WHEN
        folder = service.getOne(FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(folder)
            .isEqualTo(FileFolders.valid());
    }

    @Test
    @DisplayName("When the file folder doesn't exist, an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.findOne(FileFolderConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOne(FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileFolderNotExistingException.class);
    }

}
