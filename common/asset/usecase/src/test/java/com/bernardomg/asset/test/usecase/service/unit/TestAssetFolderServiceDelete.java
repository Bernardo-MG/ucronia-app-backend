
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

import com.bernardomg.asset.domain.exception.AssetFolderNotEmptyException;
import com.bernardomg.asset.domain.exception.AssetFolderNotExistingException;
import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.test.configuration.factory.AssetFolderConstants;
import com.bernardomg.asset.test.configuration.factory.AssetFolders;
import com.bernardomg.asset.usecase.service.DefaultAssetFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("AssetFolderService - delete")
class TestAssetFolderServiceDelete {

    @Mock
    private AssetFolderRepository     folderRepository;

    @Mock
    private AssetRepository           imageRepository;

    @InjectMocks
    private DefaultAssetFolderService service;

    @Test
    @DisplayName("With an empty folder, the image folder is deleted and returned")
    void testDelete() {
        final AssetFolder deleted;

        // GIVEN
        given(folderRepository.findOne(AssetFolderConstants.NUMBER)).willReturn(Optional.of(AssetFolders.valid()));

        // WHEN
        deleted = service.delete(AssetFolderConstants.NUMBER);

        // THEN
        verify(folderRepository).delete(AssetFolderConstants.NUMBER);
        Assertions.assertThat(deleted)
            .isEqualTo(AssetFolders.valid());
    }

    @Test
    @DisplayName("With a missing image folder, an exception is thrown")
    void testDelete_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.findOne(AssetFolderConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.delete(AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(AssetFolderNotExistingException.class);
    }

    @Test
    @DisplayName("With child folders, an exception is thrown")
    void testDelete_WithChildren() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.findOne(AssetFolderConstants.NUMBER)).willReturn(Optional.of(AssetFolders.valid()));
        given(folderRepository.hasChildren(AssetFolderConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.delete(AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(AssetFolderNotEmptyException.class);
    }

    @Test
    @DisplayName("With images, an exception is thrown")
    void testDelete_WithImages() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.findOne(AssetFolderConstants.NUMBER)).willReturn(Optional.of(AssetFolders.valid()));
        given(imageRepository.hasAssetsInFolder(AssetFolderConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.delete(AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(AssetFolderNotEmptyException.class);
    }

}
