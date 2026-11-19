
package com.bernardomg.asset.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.test.configuration.factory.Contents;
import com.bernardomg.asset.usecase.service.DefaultAssetContentService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultAssetContentService - getContent")
class TestAssetContentService {

    @Mock
    private AssetRepository            assetRepository;

    @Mock
    private ContentRepository          contentRepository;

    @InjectMocks
    private DefaultAssetContentService service;

    @Test
    @DisplayName("When the component is queried, then asset content is returned")
    void testGetContent() {
        final Content actual;
        final Asset   asset;
        final Content content;

        // GIVEN
        asset = Assets.publicAccess();
        content = Contents.pdf();
        given(contentRepository.getOne(asset.key())).willReturn(content);

        // WHEN
        actual = service.getContent(asset);

        // THEN
        Assertions.assertThat(actual)
            .as("asset content")
            .isEqualTo(content);
    }

}
