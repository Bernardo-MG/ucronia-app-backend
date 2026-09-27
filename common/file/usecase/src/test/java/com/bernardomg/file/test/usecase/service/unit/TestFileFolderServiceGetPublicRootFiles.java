
package com.bernardomg.file.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.file.domain.model.File;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.domain.repository.FileRepository;
import com.bernardomg.file.usecase.service.DefaultFileFolderService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("File folder service - get public root files")
class TestFileFolderServiceGetPublicRootFiles {

    @Mock
    private FileRepository           fileRepository;

    @Mock
    private FileFolderRepository     folderRepository;

    @InjectMocks
    private DefaultFileFolderService service;

    @Test
    @DisplayName("When reading public root files, the requested page is returned")
    void testGetPublicRootFiles() {
        final Page<File> existing;
        final Page<File> result;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, true, true, sorting);
        given(fileRepository.findAllPublicByFolder(null, pagination, sorting)).willReturn(existing);

        // WHEN
        result = service.getPublicRootFiles(pagination, sorting);

        // THEN
        Assertions.assertThat(result)
            .isEqualTo(existing);
    }

    @Test
    @DisplayName("When reading public root files with no data, the requested page is empty")
    void testGetPublicRootFiles_NoData() {
        final Page<File> existing;
        final Page<File> result;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, true, true, sorting);
        given(fileRepository.findAllPublicByFolder(null, pagination, sorting)).willReturn(existing);

        // WHEN
        result = service.getPublicRootFiles(pagination, sorting);

        // THEN
        Assertions.assertThat(result.content())
            .isEmpty();
    }

}
