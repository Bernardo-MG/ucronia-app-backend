
package com.bernardomg.asset.test.usecase.service.unit;

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

import com.bernardomg.asset.domain.exception.AssetFolderAlreadyExistsException;
import com.bernardomg.asset.domain.exception.AssetFolderNotExistingException;
import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.test.configuration.factory.AssetFolderConstants;
import com.bernardomg.asset.test.configuration.factory.AssetFolders;
import com.bernardomg.asset.usecase.service.DefaultAssetFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("AssetFolderService - update")
class TestAssetFolderServiceUpdate {

    @Mock
    private AssetFolderRepository     folderRepository;

    @Mock
    private AssetRepository           imageRepository;

    @InjectMocks
    private DefaultAssetFolderService service;

    @Test
    @DisplayName("With an existing sibling name, an exception is thrown")
    void testUpdate_ExistingName() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(AssetFolderConstants.NUMBER)).willReturn(true);
        given(folderRepository.findOne(AssetFolderConstants.NUMBER)).willReturn(Optional.of(AssetFolders.valid()));
        given(folderRepository.existsByNameAndParent(AssetFolderConstants.NAME, null, AssetFolderConstants.NUMBER))
            .willReturn(true);

        // WHEN
        execution = () -> service.update(AssetFolders.valid());

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(AssetFolderAlreadyExistsException.class);
    }

    @Test
    @DisplayName("With a missing image folder, an exception is thrown")
    void testUpdate_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(AssetFolderConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(AssetFolders.valid());

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(AssetFolderNotExistingException.class);
    }

    @Test
    @DisplayName("With valid data, the image folder is persisted")
    void testUpdate_PersistedData() {
        // GIVEN
        given(folderRepository.exists(AssetFolderConstants.NUMBER)).willReturn(true);
        given(folderRepository.findOne(AssetFolderConstants.NUMBER)).willReturn(Optional.of(AssetFolders.valid()));

        // WHEN
        service.update(AssetFolders.valid());

        // THEN
        verify(folderRepository).save(AssetFolders.valid());
    }

    @Test
    @DisplayName("With valid data, the updated image folder is returned")
    void testUpdate_ReturnedData() {
        final AssetFolder updated;

        // GIVEN
        given(folderRepository.exists(AssetFolderConstants.NUMBER)).willReturn(true);
        given(folderRepository.findOne(AssetFolderConstants.NUMBER)).willReturn(Optional.of(AssetFolders.valid()));
        given(folderRepository.save(AssetFolders.valid())).willReturn(AssetFolders.valid());

        // WHEN
        updated = service.update(AssetFolders.valid());

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(AssetFolders.valid());
    }

}
