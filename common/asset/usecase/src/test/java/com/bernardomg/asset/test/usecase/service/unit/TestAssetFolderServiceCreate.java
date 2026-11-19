
package com.bernardomg.asset.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
@DisplayName("DefaultAssetFolderService - create")
class TestAssetFolderServiceCreate {

    @Mock
    private AssetFolderRepository     folderRepository;

    @Mock
    private AssetRepository           imageRepository;

    @InjectMocks
    private DefaultAssetFolderService service;

    @Test
    @DisplayName("When there is an existing sibling name, then an exception is thrown")
    void testCreate_ExistingName() {
        final ThrowingCallable action;
        final AssetFolder      folder;

        // GIVEN
        folder = AssetFolders.toCreate();
        given(folderRepository.existsByNameAndParent(AssetFolderConstants.NAME, null, null)).willReturn(true);

        // WHEN
        action = () -> service.create(folder);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is an existing sibling name, then an exception is thrown")
            .isInstanceOf(AssetFolderAlreadyExistsException.class);
        verify(folderRepository, never()).save(folder);
    }

    @Test
    @DisplayName("When there is a missing parent, then an exception is thrown")
    void testCreate_MissingParent() {
        final ThrowingCallable action;
        final AssetFolder      folder;

        // GIVEN
        folder = AssetFolders.withParent();
        given(folderRepository.exists(AssetFolderConstants.PARENT_NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.create(folder);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a missing parent, then an exception is thrown")
            .isInstanceOf(AssetFolderNotExistingException.class);
    }

    @Test
    @DisplayName("When there is valid data, then the image folder is persisted")
    void testCreate_PersistedData() {
        // GIVEN
        given(folderRepository.existsByNameAndParent(AssetFolderConstants.NAME, null, null)).willReturn(false);

        // WHEN
        service.create(AssetFolders.toCreate());

        // THEN
        verify(folderRepository).save(AssetFolders.toCreate());
    }

    @Test
    @DisplayName("When there is valid data, then the created image folder is returned")
    void testCreate_ReturnedData() {
        final AssetFolder created;

        // GIVEN
        given(folderRepository.save(AssetFolders.toCreate())).willReturn(AssetFolders.valid());

        // WHEN
        created = service.create(AssetFolders.toCreate());

        // THEN
        Assertions.assertThat(created)
            .as("When there is valid data, then the created image folder is returned")
            .isEqualTo(AssetFolders.valid());
    }

}
