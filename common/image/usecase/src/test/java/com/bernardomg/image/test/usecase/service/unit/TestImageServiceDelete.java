
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

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
class TestImageServiceDelete {

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
    @DisplayName("When deleting an image, metadata and content are deleted")
    void testDelete() {
        final Image deleted;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.valid()));

        // WHEN
        deleted = service.delete(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(deleted)
            .isEqualTo(Images.valid());
        then(contentRepository).should()
            .delete(ImageConstants.KEY);
    }

    @Test
    @DisplayName("When deleting a missing image, not found is raised")
    void testDelete_Missing() {
        final ThrowingCallable callable;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        callable = () -> service.delete(ImageConstants.NUMBER);

        // WHEN + THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(ImageNotExistingException.class);
    }

}
