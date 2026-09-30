
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
@DisplayName("Asset service - update")
class TestAssetServiceUpdate {

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
    @DisplayName("When updating an asset, metadata and content are persisted")
    void testUpdate() {
        final Content content;
        final Asset   updated;

        // GIVEN
        given(repository.findOne(FILE, AssetConstants.NUMBER)).willReturn(Optional.of(Assets.publicAccess()));
        given(contentKeyGenerator.generate("assets")).willReturn(AssetConstants.CHANGE_KEY);
        given(repository.save(eq(FILE), any(Asset.class))).willReturn(Assets.publicAccess());
        content = Contents.pdf();

        // WHEN
        updated = service.update(Assets.publicAccess(), content);

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(Assets.publicAccess());
        then(contentRepository).should()
            .save(AssetConstants.CHANGE_KEY, content);
        then(contentRepository).should()
            .delete(AssetConstants.KEY);
    }

    @Test
    @DisplayName("When old content deletion fails, the update still succeeds")
    void testUpdate_ContentDeletionFailureIsIgnored() {
        final Asset updated;

        // GIVEN
        given(repository.findOne(FILE, AssetConstants.NUMBER)).willReturn(Optional.of(Assets.publicAccess()));
        given(contentKeyGenerator.generate("assets")).willReturn(AssetConstants.CHANGE_KEY);
        given(repository.save(eq(FILE), any(Asset.class))).willReturn(Assets.publicAccess());
        willThrow(new RuntimeException("S3 deletion failed")).given(contentRepository)
            .delete(AssetConstants.KEY);

        // WHEN
        updated = service.update(Assets.publicAccess(), Contents.pdf());

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(Assets.publicAccess());
    }

    @Test
    @DisplayName("When updating an asset with an existing name, an exception is thrown")
    void testUpdate_DuplicateName() {
        final ThrowingCallable callable;

        // GIVEN
        given(repository.findOne(FILE, AssetConstants.NUMBER)).willReturn(Optional.of(Assets.publicAccess()));
        given(repository.existsByNameAndFolder(FILE, AssetConstants.NAME, null, AssetConstants.NUMBER))
            .willReturn(true);

        // WHEN
        callable = () -> service.update(Assets.publicAccess(), Contents.pdf());

        // WHEN + THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(AssetAlreadyExistsException.class);
    }

    @Test
    @DisplayName("When update persistence fails, the replacement content is deleted")
    void testUpdate_PersistenceFailureDeletesReplacement() {
        final ThrowingCallable callable;
        final RuntimeException failure;

        // GIVEN
        failure = new RuntimeException("Persistence failed");
        given(repository.findOne(FILE, AssetConstants.NUMBER)).willReturn(Optional.of(Assets.publicAccess()));
        given(contentKeyGenerator.generate("assets")).willReturn(AssetConstants.CHANGE_KEY);
        willThrow(failure).given(repository)
            .save(any(), any());

        // WHEN
        callable = () -> service.update(Assets.publicAccess(), Contents.pdf());

        // THEN
        Assertions.assertThatThrownBy(callable)
            .isSameAs(failure);
        then(contentRepository).should()
            .delete(AssetConstants.CHANGE_KEY);
    }

    @Test
    @DisplayName("When updating an asset, metadata references the replacement content")
    void testUpdate_PersistsReplacementKey() {

        // GIVEN
        given(repository.findOne(FILE, AssetConstants.NUMBER)).willReturn(Optional.of(Assets.publicAccess()));
        given(contentKeyGenerator.generate("assets")).willReturn(AssetConstants.CHANGE_KEY);
        given(repository.save(eq(FILE), any(Asset.class))).willReturn(Assets.publicAccess());

        // WHEN
        service.update(Assets.publicAccess(), Contents.pdf());

        // THEN
        then(repository).should()
            .save(FILE, Assets.change());
    }

}
