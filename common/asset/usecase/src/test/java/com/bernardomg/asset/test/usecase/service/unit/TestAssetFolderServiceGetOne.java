
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

import com.bernardomg.asset.domain.exception.AssetFolderNotExistingException;
import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.test.configuration.factory.AssetFolderConstants;
import com.bernardomg.asset.test.configuration.factory.AssetFolders;
import com.bernardomg.asset.usecase.service.DefaultAssetFolderService;

@ExtendWith(MockitoExtension.class)
@DisplayName("AssetFolderService - get one")
class TestAssetFolderServiceGetOne {

    @Mock
    private AssetFolderRepository     folderRepository;

    @Mock
    private AssetRepository           imageRepository;

    @InjectMocks
    private DefaultAssetFolderService service;

    @Test
    @DisplayName("When the image folder exists, it is returned")
    void testGetOne() {
        final AssetFolder folder;

        // GIVEN
        given(folderRepository.findOne(AssetFolderConstants.NUMBER)).willReturn(Optional.of(AssetFolders.valid()));

        // WHEN
        folder = service.getOne(AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(folder)
            .isEqualTo(AssetFolders.valid());
    }

    @Test
    @DisplayName("When the image folder doesn't exist, an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(folderRepository.findOne(AssetFolderConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOne(AssetFolderConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(AssetFolderNotExistingException.class);
    }

}
