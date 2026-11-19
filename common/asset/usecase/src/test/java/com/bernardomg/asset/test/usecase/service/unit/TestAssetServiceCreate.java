
package com.bernardomg.asset.test.usecase.service.unit;

import static com.bernardomg.asset.domain.model.AssetType.FILE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.asset.domain.exception.AssetAlreadyExistsException;
import com.bernardomg.asset.domain.key.ContentKeyGenerator;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.asset.domain.policy.ContentPolicy;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.asset.test.configuration.factory.AssetConstants;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.test.configuration.factory.Contents;
import com.bernardomg.asset.usecase.service.DefaultAssetService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultAssetService - create")
class TestAssetServiceCreate {

    @Mock
    private ContentKeyGenerator contentKeyGenerator;

    @Mock
    private ContentPolicy       contentPolicy;

    @Mock
    private ContentRepository   contentRepository;

    @Mock
    private AssetRepository     repository;

    private DefaultAssetService service;

    @BeforeEach
    void setUp() {
        service = new DefaultAssetService(FILE, "assets", repository, contentRepository, contentPolicy,
            contentKeyGenerator);
    }

    @Test
    @DisplayName("When creating an asset with an existing name, then conflict is raised")
    void testCreate_Existing() {
        final ThrowingCallable action;

        // GIVEN
        given(repository.existsByNameAndFolder(AssetConstants.NAME, null)).willReturn(true);

        // WHEN
        action = () -> service.create(Assets.publicAccess(), Contents.pdf());

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When creating an asset with an existing name, then conflict is raised")
            .isInstanceOf(AssetAlreadyExistsException.class);
    }

    @Test
    @DisplayName("When creating an asset, then the name is checked in its folder")
    void testCreate_NameCheckedInFolder() {
        final Long  folderNumber;
        final Asset asset;

        // GIVEN
        folderNumber = 2L;
        asset = new Asset(FILE, AssetConstants.NUMBER, AssetConstants.NAME, AssetConstants.DESCRIPTION,
            AssetConstants.KEY, AssetConstants.PDF_MEDIA_TYPE, AssetConstants.DATA.length, Optional.of(folderNumber));
        given(contentKeyGenerator.generate("assets")).willReturn(AssetConstants.KEY);
        given(repository.save(eq(FILE), any(Asset.class))).willReturn(asset);

        // WHEN
        service.create(asset, Contents.pdf());

        // THEN
        then(repository).should()
            .existsByNameAndFolder(AssetConstants.NAME, folderNumber);
    }

    @Test
    @DisplayName("When creating an asset, then the content should be persisted")
    void testCreate_PersistContent() {
        final Content content;

        // GIVEN
        given(contentKeyGenerator.generate("assets")).willReturn(AssetConstants.KEY);
        given(repository.save(eq(FILE), any(Asset.class))).willReturn(Assets.publicAccess());
        content = Contents.pdf();

        // WHEN
        service.create(Assets.publicAccess(), content);

        // THEN
        then(contentRepository).should()
            .save(AssetConstants.KEY, content);
    }

    @Test
    @DisplayName("When metadata persistence fails, then the uploaded content is deleted")
    void testCreate_PersistenceFailureDeletesContent() {
        final ThrowingCallable action;
        final RuntimeException failure;

        // GIVEN
        failure = new RuntimeException("Persistence failed");
        given(contentKeyGenerator.generate("assets")).willReturn(AssetConstants.KEY);
        willThrow(failure).given(repository)
            .save(any(), any());

        // WHEN
        action = () -> service.create(Assets.publicAccess(), Contents.pdf());

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When metadata persistence fails, then the uploaded content is deleted")
            .isSameAs(failure);
        then(contentRepository).should()
            .delete(AssetConstants.KEY);
    }

    @Test
    @DisplayName("When creating an asset, then the correct asset is returned")
    void testCreate_Returned() {
        final Content content;
        final Asset   created;

        // GIVEN
        given(contentKeyGenerator.generate("assets")).willReturn(AssetConstants.KEY);
        given(repository.save(eq(FILE), any(Asset.class))).willReturn(Assets.publicAccess());
        content = Contents.pdf();

        // WHEN
        created = service.create(Assets.publicAccess(), content);

        // THEN
        Assertions.assertThat(created)
            .as("When creating an asset, then the correct asset is returned")
            .isEqualTo(Assets.publicAccess());
    }

}
