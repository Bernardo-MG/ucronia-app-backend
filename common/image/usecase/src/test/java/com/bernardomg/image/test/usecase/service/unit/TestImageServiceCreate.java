
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.content.domain.key.ContentKeyGenerator;
import com.bernardomg.content.domain.model.Content;
import com.bernardomg.content.domain.policy.ContentPolicy;
import com.bernardomg.content.domain.repository.ContentRepository;
import com.bernardomg.image.domain.exception.ImageAlreadyExistsException;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.Contents;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.image.usecase.service.DefaultImageService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service")
class TestImageServiceCreate {

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
    @DisplayName("When creating an image with an existing name, conflict is raised")
    void testCreate_Existing() {
        final ThrowingCallable callable;

        // GIVEN
        given(repository.existsByName(ImageConstants.NAME)).willReturn(true);

        // WHEN
        callable = () -> service.create(Images.valid(), Contents.image());

        // THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(ImageAlreadyExistsException.class);
    }

    @Test
    @DisplayName("When creating an image, the content should be persisted")
    void testCreate_PersistContent() {
        final Content content;
        // GIVEN
        given(contentKeyGenerator.generate("images")).willReturn(ImageConstants.KEY);
        given(repository.save(any(Image.class))).willReturn(Images.valid());
        content = Contents.image();

        service.create(Images.valid(), content);

        // THEN
        then(contentRepository).should()
            .save(ImageConstants.KEY, content);
    }

    @Test
    @DisplayName("When creating an image, the correct image is returned")
    void testCreate_Returned() {
        final Content content;
        final Image   created;

        // GIVEN
        given(contentKeyGenerator.generate("images")).willReturn(ImageConstants.KEY);
        given(repository.save(any(Image.class))).willReturn(Images.valid());
        content = Contents.image();

        // WHEN
        created = service.create(Images.valid(), content);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Images.valid());
    }

}
