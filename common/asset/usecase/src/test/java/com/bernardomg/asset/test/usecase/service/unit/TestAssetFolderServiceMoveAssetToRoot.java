
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
import com.bernardomg.asset.domain.exception.AssetNotExistingException;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.test.configuration.factory.AssetFolderConstants;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.usecase.service.DefaultAssetFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("AssetFolderService - move image to root")
class TestAssetFolderServiceMoveAssetToRoot {

    @Mock
    private AssetFolderRepository     folderRepository;

    @Mock
    private AssetRepository           imageRepository;

    @InjectMocks
    private DefaultAssetFolderService service;

    @Test
    @DisplayName("With an existing image, the image is moved to root")
    void testMoveImageToRoot() {
        final Asset moved;

        // GIVEN
        given(imageRepository.findOne(AssetFolderConstants.IMAGE_NUMBER))
            .willReturn(Optional.of(Assets.publicAccess()));
        given(imageRepository.move(AssetFolderConstants.IMAGE_NUMBER, null)).willReturn(Assets.publicAccess());

        // WHEN
        moved = service.moveAssetToRoot(AssetFolderConstants.IMAGE_NUMBER);

        // THEN
        Assertions.assertThat(moved)
            .isEqualTo(Assets.publicAccess());
    }

    @Test
    @DisplayName("With a missing image, an exception is thrown")
    void testMoveImageToRoot_MissingImage() {
        final ThrowingCallable execution;

        // GIVEN
        given(imageRepository.findOne(AssetFolderConstants.IMAGE_NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.moveAssetToRoot(AssetFolderConstants.IMAGE_NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(AssetNotExistingException.class);
    }

    @Test
    @DisplayName("With the same name in the root folder, an exception is thrown")
    void testMoveImageToRoot_NameConflict() {
        final ThrowingCallable execution;

        // GIVEN
        given(imageRepository.findOne(AssetFolderConstants.IMAGE_NUMBER))
            .willReturn(Optional.of(Assets.publicAccess()));
        given(imageRepository.existsByNameAndFolder(Assets.publicAccess()
            .name(), null,
            Assets.publicAccess()
                .number())).willReturn(true);

        // WHEN
        execution = () -> service.moveAssetToRoot(AssetFolderConstants.IMAGE_NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(AssetAlreadyExistsException.class);
    }

}
