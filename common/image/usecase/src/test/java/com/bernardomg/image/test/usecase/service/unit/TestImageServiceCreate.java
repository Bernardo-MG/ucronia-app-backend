
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import com.bernardomg.image.domain.exception.ImageAlreadyExistsException;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.model.ImageContent;
import com.bernardomg.image.domain.repository.ImageContentRepository;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.image.usecase.service.DefaultImageService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service")
class TestImageServiceCreate {

    @Mock
    private ImageContentRepository contentRepository;

    @Mock
    private ImageRepository        repository;

    @InjectMocks
    private DefaultImageService    service;

    @Test
    @DisplayName("When creating an image, metadata and content are persisted")
    void testCreate() {
        final Image created;

        // GIVEN
        given(repository.save(any(Image.class))).willReturn(Images.valid());

        // WHEN
        created = service.create(Images.valid(), new ImageContent(new ByteArrayInputStream(ImageConstants.DATA),
            ImageConstants.DATA.length, ImageConstants.PNG_MEDIA_TYPE));

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Images.valid());
    }

    @Test
    @DisplayName("When creating an image with an existing name, conflict is raised")
    void testCreate_Existing() {
        final ThrowingCallable callable;

        // GIVEN
        given(repository.existsByName(ImageConstants.NAME)).willReturn(true);

        // WHEN
        callable = () -> service.create(Images.valid(),
            new ImageContent(new ByteArrayInputStream(ImageConstants.DATA), ImageConstants.DATA.length,
                ImageConstants.PNG_MEDIA_TYPE));

        // THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(ImageAlreadyExistsException.class);
    }

}
