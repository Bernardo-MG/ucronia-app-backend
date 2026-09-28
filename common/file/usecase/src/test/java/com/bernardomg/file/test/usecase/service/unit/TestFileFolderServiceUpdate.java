
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

import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.file.domain.exception.FileFolderAlreadyExistsException;
import com.bernardomg.file.domain.exception.FileFolderNotExistingException;
import com.bernardomg.file.test.configuration.factory.FileFolderConstants;
import com.bernardomg.file.test.configuration.factory.FileFolders;
import com.bernardomg.file.usecase.service.DefaultFileFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("FileFolderService - update")
class TestFileFolderServiceUpdate {

    @Mock
    private AssetRepository          fileRepository;

    @Mock
    private AssetFolderRepository    folderRepository;

    @InjectMocks
    private DefaultFileFolderService service;

    @Test
    @DisplayName("With an existing sibling name, an exception is thrown")
    void testUpdate_ExistingName() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(FileFolderConstants.NUMBER)).willReturn(true);
        given(folderRepository.findOne(FileFolderConstants.NUMBER)).willReturn(Optional.of(FileFolders.valid()));
        given(folderRepository.existsByNameAndParent(FileFolderConstants.NAME, null, FileFolderConstants.NUMBER))
            .willReturn(true);

        // WHEN
        execution = () -> service.update(FileFolders.valid());

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileFolderAlreadyExistsException.class);
    }

    @Test
    @DisplayName("With a missing file folder, an exception is thrown")
    void testUpdate_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(FileFolderConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(FileFolders.valid());

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileFolderNotExistingException.class);
    }

    @Test
    @DisplayName("With valid data, the file folder is persisted")
    void testUpdate_PersistedData() {
        // GIVEN
        given(folderRepository.exists(FileFolderConstants.NUMBER)).willReturn(true);
        given(folderRepository.findOne(FileFolderConstants.NUMBER)).willReturn(Optional.of(FileFolders.valid()));

        // WHEN
        service.update(FileFolders.valid());

        // THEN
        verify(folderRepository).save(FileFolders.valid());
    }

    @Test
    @DisplayName("With valid data, the updated file folder is returned")
    void testUpdate_ReturnedData() {
        final AssetFolder updated;

        // GIVEN
        given(folderRepository.exists(FileFolderConstants.NUMBER)).willReturn(true);
        given(folderRepository.findOne(FileFolderConstants.NUMBER)).willReturn(Optional.of(FileFolders.valid()));
        given(folderRepository.save(FileFolders.valid())).willReturn(FileFolders.valid());

        // WHEN
        updated = service.update(FileFolders.valid());

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(FileFolders.valid());
    }

}
