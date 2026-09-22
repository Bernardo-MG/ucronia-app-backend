
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.content.domain.policy.ContentPolicy;
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
class TestImageServiceUpdate {

    @Mock
    private ContentPolicy          contentPolicy;

    @Mock
    private ImageContentRepository contentRepository;

    @Mock
    private ImageRepository        repository;

    @InjectMocks
    private DefaultImageService    service;

    @Test
    @DisplayName("When updating an image, metadata and content are persisted")
    void testUpdate() {
        final Image updated;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.valid()));
        given(repository.save(any(Image.class))).willReturn(Images.valid());

        // WHEN
        updated = service.update(Images.valid(), new ImageContent(new ByteArrayInputStream(ImageConstants.DATA),
            ImageConstants.DATA.length, ImageConstants.PNG_MEDIA_TYPE));

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(Images.valid());
    }

    @Test
    @DisplayName("When updating an image with an existing name, an exception is thrown")
    void testUpdate_DuplicateName() {
        final ThrowingCallable callable;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.valid()));
        given(repository.existsByNameForAnother(ImageConstants.NAME, ImageConstants.NUMBER)).willReturn(true);

        // WHEN
        callable = () -> service.update(Images.valid(), new ImageContent(new ByteArrayInputStream(ImageConstants.DATA),
            ImageConstants.DATA.length, ImageConstants.PNG_MEDIA_TYPE));

        // WHEN + THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(ImageAlreadyExistsException.class);
    }

}
