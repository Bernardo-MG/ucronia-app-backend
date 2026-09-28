
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.content.domain.key.ContentKeyGenerator;
import com.bernardomg.content.domain.policy.ContentPolicy;
import com.bernardomg.content.domain.repository.ContentRepository;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.image.usecase.service.DefaultImageService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Asset service - update metadata")
class TestImageServiceUpdateMetadata {

    @Mock
    private ContentKeyGenerator contentKeyGenerator;

    @Mock
    private ContentPolicy       contentPolicy;

    @Mock
    private ContentRepository   contentRepository;

    @Mock
    private AssetRepository     repository;

    @InjectMocks
    private DefaultImageService service;

    @Test
    @DisplayName("When updating image metadata, content is not persisted")
    void testUpdateMetadata() {
        final Asset updated;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.publicAccess()));
        given(repository.save(any(Asset.class))).willReturn(Images.publicAccess());

        // WHEN
        updated = service.updateMetadata(Images.publicAccess());

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(Images.publicAccess());
    }

}
