
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
@DisplayName("DefaultAssetFolderService - moveAsset")
class TestAssetFolderServiceMoveAsset {

    @Mock
    private AssetFolderRepository     folderRepository;

    @Mock
    private AssetRepository           imageRepository;

    @InjectMocks
    private DefaultAssetFolderService service;

    @Test
    @DisplayName("When there is an existing image and folder, then the image is moved")
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
            .as("When there is an existing image and folder, then the image is moved")
            .isEqualTo(Assets.publicAccess());
    }

    @Test
    @DisplayName("When there is a missing folder, then an exception is thrown")
    void testMoveImage_MissingFolder() {
        final ThrowingCallable action;

        // GIVEN
        given(folderRepository.exists(AssetFolderConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.moveAsset(AssetFolderConstants.IMAGE_NUMBER, AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a missing folder, then an exception is thrown")
            .isInstanceOf(AssetFolderNotExistingException.class);
    }

    @Test
    @DisplayName("When there is a missing image, then an exception is thrown")
    void testMoveImage_MissingImage() {
        final ThrowingCallable action;

        // GIVEN
        given(folderRepository.exists(AssetFolderConstants.NUMBER)).willReturn(true);
        given(imageRepository.findOne(AssetFolderConstants.IMAGE_NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.moveAsset(AssetFolderConstants.IMAGE_NUMBER, AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a missing image, then an exception is thrown")
            .isInstanceOf(AssetNotExistingException.class);
    }

    @Test
    @DisplayName("When the same name in the target folder is provided, then an exception is thrown")
    void testMoveImage_NameConflict() {
        final ThrowingCallable action;

        // GIVEN
        given(folderRepository.exists(AssetFolderConstants.NUMBER)).willReturn(true);
        given(imageRepository.findOne(AssetFolderConstants.IMAGE_NUMBER))
            .willReturn(Optional.of(Assets.publicAccess()));
        given(imageRepository.existsByNameAndFolder(Assets.publicAccess()
            .name(), AssetFolderConstants.NUMBER,
            Assets.publicAccess()
                .number())).willReturn(true);

        // WHEN
        action = () -> service.moveAsset(AssetFolderConstants.IMAGE_NUMBER, AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the same name in the target folder is provided, then an exception is thrown")
            .isInstanceOf(AssetAlreadyExistsException.class);
    }

}
