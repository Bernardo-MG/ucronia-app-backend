
package com.bernardomg.file.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.file.test.configuration.factory.FileFolders;
import com.bernardomg.file.usecase.service.DefaultFileFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("FileFolderService - get all")
class TestFileFolderServiceGetAll {

    @Mock
    private AssetRepository          fileRepository;

    @Mock
    private AssetFolderRepository    folderRepository;

    @InjectMocks
    private DefaultFileFolderService service;

    @Test
    @DisplayName("When reading all file folders, all file folders are returned")
    void testGetAll() {
        final Collection<AssetFolder> folders;

        // GIVEN
        given(folderRepository.findAll()).willReturn(List.of(FileFolders.valid()));

        // WHEN
        folders = service.getAll();

        // THEN
        Assertions.assertThat(folders)
            .containsExactly(FileFolders.valid());
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testGetAll_Empty() {
        final Collection<AssetFolder> folders;

        // GIVEN
        given(folderRepository.findAll()).willReturn(List.of());

        // WHEN
        folders = service.getAll();

        // THEN
        Assertions.assertThat(folders)
            .isEmpty();
    }

}
