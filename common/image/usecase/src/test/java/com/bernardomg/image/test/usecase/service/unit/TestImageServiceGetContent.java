
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.key.ContentKeyGenerator;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.asset.domain.policy.ContentPolicy;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.image.test.configuration.factory.Contents;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.image.usecase.service.DefaultImageService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Asset service - get content")
class TestImageServiceGetContent {

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
    @DisplayName("When getting image content, it is loaded from storage")
    void testGetContent() throws IOException {
        final Content content;
        final Content existing;

        // GIVEN
        existing = Contents.image();
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.publicAccess()));
        given(contentRepository.getOne(ImageConstants.KEY)).willReturn(existing);

        // WHEN
        content = service.getContent(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(content.data()
            .readAllBytes())
            .containsExactly(ImageConstants.DATA);
    }

}
