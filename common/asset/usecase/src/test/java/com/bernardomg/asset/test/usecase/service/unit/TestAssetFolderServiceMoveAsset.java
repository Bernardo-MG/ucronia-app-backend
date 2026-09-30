
package com.bernardomg.asset.test.usecase.service.unit;

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

import com.bernardomg.asset.domain.exception.AssetAlreadyExistsException;
import com.bernardomg.asset.domain.exception.AssetFolderNotExistingException;
import com.bernardomg.asset.domain.exception.AssetNotExistingException;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.test.configuration.factory.AssetFolderConstants;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.usecase.service.DefaultAssetFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("AssetFolderService - move image")
class TestAssetFolderServiceMoveAsset {

    @Mock
    private AssetFolderRepository     folderRepository;

    @Mock
    private AssetRepository           imageRepository;

    @InjectMocks
    private DefaultAssetFolderService service;

    @Test
    @DisplayName("With an existing image and folder, the image is moved")
    void testMoveImage() {
        final Asset moved;

        // GIVEN
        given(folderRepository.exists(AssetFolderConstants.NUMBER)).willReturn(true);
        given(imageRepository.findOne(AssetFolderConstants.IMAGE_NUMBER))
            .willReturn(Optional.of(Assets.publicAccess()));
        given(imageRepository.move(AssetFolderConstants.IMAGE_NUMBER, AssetFolderConstants.NUMBER))
            .willReturn(Assets.publicAccess());

        // WHEN
        moved = service.moveAsset(AssetFolderConstants.IMAGE_NUMBER, AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(moved)
            .isEqualTo(Assets.publicAccess());
    }

    @Test
    @DisplayName("With a missing folder, an exception is thrown")
    void testMoveImage_MissingFolder() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(AssetFolderConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.moveAsset(AssetFolderConstants.IMAGE_NUMBER, AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(AssetFolderNotExistingException.class);
    }

    @Test
    @DisplayName("With a missing image, an exception is thrown")
    void testMoveImage_MissingImage() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(AssetFolderConstants.NUMBER)).willReturn(true);
        given(imageRepository.findOne(AssetFolderConstants.IMAGE_NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.moveAsset(AssetFolderConstants.IMAGE_NUMBER, AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(AssetNotExistingException.class);
    }

    @Test
    @DisplayName("With the same name in the target folder, an exception is thrown")
    void testMoveImage_NameConflict() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.exists(AssetFolderConstants.NUMBER)).willReturn(true);
        given(imageRepository.findOne(AssetFolderConstants.IMAGE_NUMBER))
            .willReturn(Optional.of(Assets.publicAccess()));
        given(imageRepository.existsByNameAndFolder(Assets.publicAccess()
            .name(), AssetFolderConstants.NUMBER,
            Assets.publicAccess()
                .number())).willReturn(true);

        // WHEN
        execution = () -> service.moveAsset(AssetFolderConstants.IMAGE_NUMBER, AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(AssetAlreadyExistsException.class);
    }

}
