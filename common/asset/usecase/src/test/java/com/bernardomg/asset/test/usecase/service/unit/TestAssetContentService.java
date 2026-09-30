
package com.bernardomg.asset.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.asset.domain.exception.AssetNotExistingException;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.asset.test.configuration.factory.AssetConstants;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.test.configuration.factory.Contents;
import com.bernardomg.asset.usecase.service.DefaultAssetContentService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Asset content service")
class TestAssetContentService {

    @Mock
    private AssetRepository            assetRepository;

    @Mock
    private ContentRepository          contentRepository;

    @InjectMocks
    private DefaultAssetContentService service;

    @Test
    @DisplayName("Returns asset content")
    void testGetContent() {
        final Asset   asset;
        final Content content;

        // GIVEN
        asset = Assets.publicAccess();
        content = Contents.pdf();
        given(contentRepository.getOne(asset.key())).willReturn(content);

        // WHEN + THEN
        Assertions.assertThat(service.getContent(asset))
            .isEqualTo(content);
    }

    @Test
    @DisplayName("Returns an existing asset")
    void testGetOne() {
        final Asset asset;

        // GIVEN
        asset = Assets.publicAccess();
        given(assetRepository.findOne(AssetConstants.NUMBER)).willReturn(Optional.of(asset));

        // WHEN + THEN
        Assertions.assertThat(service.getOne(AssetConstants.NUMBER))
            .isEqualTo(asset);
    }

    @Test
    @DisplayName("Rejects a missing asset")
    void testGetOne_NotFound() {
        // GIVEN
        given(assetRepository.findOne(AssetConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN + THEN
        Assertions.assertThatThrownBy(() -> service.getOne(AssetConstants.NUMBER))
            .isInstanceOf(AssetNotExistingException.class);
    }

}
