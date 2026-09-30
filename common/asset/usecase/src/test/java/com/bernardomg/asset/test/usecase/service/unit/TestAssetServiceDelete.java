
package com.bernardomg.asset.test.usecase.service.unit;

import static com.bernardomg.asset.domain.model.AssetType.FILE;
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

import com.bernardomg.asset.domain.exception.AssetNotExistingException;
import com.bernardomg.asset.domain.key.ContentKeyGenerator;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.policy.ContentPolicy;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.asset.test.configuration.factory.AssetConstants;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.usecase.service.DefaultAssetService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Asset service - delete")
class TestAssetServiceDelete {

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
    @DisplayName("When deleting an asset, the asset is deleted")
    void testDelete() {
        // GIVEN
        given(repository.findOne(FILE, AssetConstants.NUMBER)).willReturn(Optional.of(Assets.publicAccess()));

        // WHEN
        service.delete(AssetConstants.NUMBER);

        // THEN
        then(contentRepository).should()
            .delete(AssetConstants.KEY);
    }

    @Test
    @DisplayName("When content deletion fails, the metadata deletion still succeeds")
    void testDelete_ContentDeletionFailureIsIgnored() {

        // GIVEN
        given(repository.findOne(FILE, AssetConstants.NUMBER)).willReturn(Optional.of(Assets.publicAccess()));
        willThrow(new RuntimeException("S3 deletion failed")).given(contentRepository)
            .delete(AssetConstants.KEY);

        // WHEN
        service.delete(AssetConstants.NUMBER);

        // THEN
        then(repository).should()
            .delete(FILE, AssetConstants.NUMBER);
    }

    @Test
    @DisplayName("When metadata deletion fails, the content is preserved")
    void testDelete_MetadataDeletionFailurePreservesContent() {
        final RuntimeException exception;
        final ThrowingCallable execution;

        // GIVEN
        exception = new RuntimeException("Database deletion failed");
        given(repository.findOne(FILE, AssetConstants.NUMBER)).willReturn(Optional.of(Assets.publicAccess()));
        willThrow(exception).given(repository)
            .delete(FILE, AssetConstants.NUMBER);

        // WHEN
        execution = () -> service.delete(AssetConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isSameAs(exception);
        then(contentRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("When deleting a missing asset, not found is raised")
    void testDelete_Missing() {
        final ThrowingCallable callable;

        // GIVEN
        given(repository.findOne(FILE, AssetConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        callable = () -> service.delete(AssetConstants.NUMBER);

        // WHEN + THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(AssetNotExistingException.class);
    }

    @Test
    @DisplayName("When deleting an asset, the deleted asset is returned")
    void testDelete_Returned() {
        final Asset deleted;

        // GIVEN
        given(repository.findOne(FILE, AssetConstants.NUMBER)).willReturn(Optional.of(Assets.publicAccess()));

        // WHEN
        deleted = service.delete(AssetConstants.NUMBER);

        // THEN
        Assertions.assertThat(deleted)
            .isEqualTo(Assets.publicAccess());
    }

}
