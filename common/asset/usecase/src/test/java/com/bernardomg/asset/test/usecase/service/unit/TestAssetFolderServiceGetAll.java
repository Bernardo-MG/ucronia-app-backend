
package com.bernardomg.asset.test.usecase.service.unit;

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
import com.bernardomg.asset.test.configuration.factory.AssetFolders;
import com.bernardomg.asset.usecase.service.DefaultAssetFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("AssetFolderService - get all")
class TestAssetFolderServiceGetAll {

    @Mock
    private AssetFolderRepository     folderRepository;

    @Mock
    private AssetRepository           imageRepository;

    @InjectMocks
    private DefaultAssetFolderService service;

    @Test
    @DisplayName("When reading all image folders, all image folders are returned")
    void testGetAll() {
        final Collection<AssetFolder> folders;

        // GIVEN
        given(folderRepository.findAll()).willReturn(List.of(AssetFolders.valid()));

        // WHEN
        folders = service.getAll();

        // THEN
        Assertions.assertThat(folders)
            .containsExactly(AssetFolders.valid());
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
