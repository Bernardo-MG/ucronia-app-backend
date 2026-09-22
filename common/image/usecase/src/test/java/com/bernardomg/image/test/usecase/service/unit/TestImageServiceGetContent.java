
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.content.domain.policy.ContentPolicy;
import com.bernardomg.image.domain.model.ImageContent;
import com.bernardomg.image.domain.repository.ImageContentRepository;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.image.usecase.service.DefaultImageService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service")
class TestImageServiceGetContent {

    @Mock
    private ContentPolicy          contentPolicy;

    @Mock
    private ImageContentRepository contentRepository;

    @Mock
    private ImageRepository        repository;

    @InjectMocks
    private DefaultImageService    service;

    @Test
    @DisplayName("When getting image content, it is loaded from storage")
    void testGetContent() throws IOException {
        final ImageContent content;
        final ImageContent existing;

        // GIVEN
        existing = new ImageContent(new ByteArrayInputStream(ImageConstants.DATA), ImageConstants.DATA.length,
            ImageConstants.PNG_MEDIA_TYPE);
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.valid()));
        given(contentRepository.getOne(ImageConstants.KEY)).willReturn(existing);

        // WHEN
        content = service.getContent(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(content.data()
            .readAllBytes())
            .containsExactly(ImageConstants.DATA);
    }

}
