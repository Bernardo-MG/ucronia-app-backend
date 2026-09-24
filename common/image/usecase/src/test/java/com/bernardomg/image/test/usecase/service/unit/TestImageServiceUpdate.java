
package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;

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
class TestImageServiceUpdate {

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
    @DisplayName("When updating an image, metadata and content are persisted")
    void testUpdate() {
        final Content content;
        final Image   updated;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.valid()));
        given(contentKeyGenerator.generate("images")).willReturn(ImageConstants.CHANGE_KEY);
        given(repository.save(any(Image.class))).willReturn(Images.valid());
        content = Contents.image();

        // WHEN
        updated = service.update(Images.valid(), content);

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(Images.valid());
        then(contentRepository).should()
            .save(ImageConstants.CHANGE_KEY, content);
        then(contentRepository).should()
            .delete(ImageConstants.KEY);
    }

    @Test
    @DisplayName("When old content deletion fails, the update still succeeds")
    void testUpdate_ContentDeletionFailureIsIgnored() {
        final Image updated;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.valid()));
        given(contentKeyGenerator.generate("images")).willReturn(ImageConstants.CHANGE_KEY);
        given(repository.save(any(Image.class))).willReturn(Images.valid());
        willThrow(new RuntimeException("S3 deletion failed")).given(contentRepository)
            .delete(ImageConstants.KEY);

        // WHEN
        updated = service.update(Images.valid(), Contents.image());

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
        given(repository.existsByNameAndFolder(ImageConstants.NAME, null, ImageConstants.NUMBER)).willReturn(true);

        // WHEN
        callable = () -> service.update(Images.valid(), Contents.image());

        // WHEN + THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(ImageAlreadyExistsException.class);
    }

    @Test
    @DisplayName("When update persistence fails, the replacement content is deleted")
    void testUpdate_PersistenceFailureDeletesReplacement() {
        final ThrowingCallable callable;
        final RuntimeException failure;

        // GIVEN
        failure = new RuntimeException("Persistence failed");
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.valid()));
        given(contentKeyGenerator.generate("images")).willReturn(ImageConstants.CHANGE_KEY);
        willThrow(failure).given(repository)
            .save(any(Image.class));

        // WHEN
        callable = () -> service.update(Images.valid(), Contents.image());

        // THEN
        Assertions.assertThatThrownBy(callable)
            .isSameAs(failure);
        then(contentRepository).should()
            .delete(ImageConstants.CHANGE_KEY);
    }

    @Test
    @DisplayName("When updating an image, metadata references the replacement content")
    void testUpdate_PersistsReplacementKey() {

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.valid()));
        given(contentKeyGenerator.generate("images")).willReturn(ImageConstants.CHANGE_KEY);
        given(repository.save(any(Image.class))).willReturn(Images.valid());

        // WHEN
        service.update(Images.valid(), Contents.image());

        // THEN
        then(repository).should()
            .save(Images.change());
    }

}
