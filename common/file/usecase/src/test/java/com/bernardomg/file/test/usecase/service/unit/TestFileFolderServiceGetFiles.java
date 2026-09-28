
package com.bernardomg.file.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

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
import com.bernardomg.file.domain.exception.FileFolderNotExistingException;
import com.bernardomg.file.test.configuration.factory.FileFolderConstants;
import com.bernardomg.file.usecase.service.DefaultFileFolderService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("FileFolderService - get files")
class TestFileFolderServiceGetFiles {

    @Mock
    private AssetRepository          fileRepository;

    @Mock
    private AssetFolderRepository    folderRepository;

    @InjectMocks
    private DefaultFileFolderService service;

    @Test
    @DisplayName("With an existing folder, the requested file page is returned")
    void testGetFiles() {
        final Page<Asset> result;
        final Page<Asset> existing;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, true, true, sorting);

        given(folderRepository.exists(FileFolderConstants.NUMBER)).willReturn(true);
        given(fileRepository.findAllByFolder(FileFolderConstants.NUMBER, pagination, sorting)).willReturn(existing);

        // WHEN
        result = service.getFiles(FileFolderConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(result)
            .isEqualTo(existing);
    }

    @Test
    @DisplayName("With a missing folder, an exception is thrown")
    void testGetFiles_NotExisting() {
        final ThrowingCallable execution;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();
        given(folderRepository.exists(FileFolderConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.getFiles(FileFolderConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(FileFolderNotExistingException.class);
    }

}
