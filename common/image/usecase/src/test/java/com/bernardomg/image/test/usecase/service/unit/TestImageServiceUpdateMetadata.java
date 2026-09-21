
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

import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.repository.ImageContentRepository;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.image.usecase.service.DefaultImageService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service")
class TestImageServiceUpdateMetadata {

    @Mock
    private ImageContentRepository contentRepository;

    @Mock
    private ImageRepository        repository;

    @InjectMocks
    private DefaultImageService    service;

    @Test
    @DisplayName("When updating image metadata, content is not persisted")
    void testUpdateMetadata() {
        final Image updated;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.valid()));
        given(repository.save(any(Image.class))).willReturn(Images.valid());

        // WHEN
        updated = service.updateMetadata(Images.valid());

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(Images.valid());
    }

}
