
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

import com.bernardomg.asset.domain.exception.AssetNotExistingException;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.asset.test.configuration.factory.AssetConstants;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.usecase.service.DefaultAssetContentService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultAssetContentService - getOne")
class TestAssetContentServiceGetOne {

    @Mock
    private AssetRepository            assetRepository;

    @Mock
    private ContentRepository          contentRepository;

    @InjectMocks
    private DefaultAssetContentService service;

    @Test
    @DisplayName("When the component is queried, then an existing asset is returned")
    void testGetOne() {
        final Asset actual;
        final Asset asset;

        // GIVEN
        asset = Assets.publicAccess();
        given(assetRepository.findOne(AssetConstants.NUMBER)).willReturn(Optional.of(asset));

        // WHEN
        actual = service.getOne(AssetConstants.NUMBER);

        // THEN
        Assertions.assertThat(actual)
            .as("existing asset")
            .isEqualTo(asset);
    }

    @Test
    @DisplayName("When a missing asset is requested, then it is rejected")
    void testGetOne_NotFound() {
        final ThrowingCallable action;

        // GIVEN
        given(assetRepository.findOne(AssetConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(AssetConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("missing asset rejection")
            .isInstanceOf(AssetNotExistingException.class);
    }

}
