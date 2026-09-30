
package com.bernardomg.asset.test.usecase.service.unit;

import static com.bernardomg.asset.domain.model.AssetType.FILE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.asset.domain.key.ContentKeyGenerator;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.policy.ContentPolicy;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.asset.test.configuration.factory.AssetConstants;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.usecase.service.DefaultAssetService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Asset service - update metadata")
class TestAssetServiceUpdateMetadata {

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
    @DisplayName("When updating asset metadata, content is not persisted")
    void testUpdateMetadata() {
        final Asset updated;

        // GIVEN
        given(repository.findOne(FILE, AssetConstants.NUMBER)).willReturn(Optional.of(Assets.publicAccess()));
        given(repository.save(eq(FILE), any(Asset.class))).willReturn(Assets.publicAccess());

        // WHEN
        updated = service.updateMetadata(Assets.publicAccess());

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(Assets.publicAccess());
    }

}
