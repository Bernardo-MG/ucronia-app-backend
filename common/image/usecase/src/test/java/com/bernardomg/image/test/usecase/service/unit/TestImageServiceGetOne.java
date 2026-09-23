
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.content.domain.key.ContentKeyGenerator;
import com.bernardomg.content.domain.policy.ContentPolicy;
import com.bernardomg.content.domain.repository.ContentRepository;
import com.bernardomg.image.domain.exception.ImageNotExistingException;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.image.usecase.service.DefaultImageService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service")
class TestImageServiceGetOne {

    @Mock
    private ContentKeyGenerator contentKeyGenerator;

    @Mock
    private ContentPolicy       contentPolicy;

    @Mock
    private ContentRepository   contentRepository;

    @Mock
    private ImageRepository     repository;

    @InjectMocks
    private DefaultImageService service;

    @Test
    @DisplayName("When getting an image, its metadata is returned")
    void testGetOne() {
        final Image result;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.valid()));

        // WHEN
        result = service.getOne(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(result)
            .isEqualTo(Images.valid());
    }

    @Test
    @DisplayName("When getting a missing image, not found is raised")
    void testGetOne_Missing() {
        final ThrowingCallable callable;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        callable = () -> service.getOne(ImageConstants.NUMBER);

        // WHEN + THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(ImageNotExistingException.class);
    }

}
