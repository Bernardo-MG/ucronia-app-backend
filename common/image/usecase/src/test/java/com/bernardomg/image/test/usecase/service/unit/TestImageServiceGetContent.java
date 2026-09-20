
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private ImageContentRepository contentRepository;

    @Mock
    private ImageRepository        repository;

    @InjectMocks
    private DefaultImageService    service;

    @Test
    @DisplayName("When getting image content, it is loaded from storage")
    void testGetContent() {
        final ImageContent content;
        final ImageContent existing;

        // GIVEN
        existing = new ImageContent(ImageConstants.DATA, ImageConstants.PNG_MEDIA_TYPE);
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.valid()));
        given(contentRepository.getOne(ImageConstants.KEY)).willReturn(existing);

        // WHEN
        content = service.getContent(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(content.data())
            .containsExactly(ImageConstants.DATA);
    }

}
