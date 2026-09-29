
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

import com.bernardomg.asset.domain.exception.AssetAlreadyExistsException;
import com.bernardomg.asset.domain.model.Asset;
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
@DisplayName("Asset service - update")
class TestImageServiceUpdate {

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
    @DisplayName("When updating an image, metadata and content are persisted")
    void testUpdate() {
        final Content content;
        final Asset   updated;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.publicAccess()));
        given(contentKeyGenerator.generate("images")).willReturn(ImageConstants.CHANGE_KEY);
        given(repository.save(any(Asset.class))).willReturn(Images.publicAccess());
        content = Contents.image();

        // WHEN
        updated = service.update(Images.publicAccess(), content);

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(Images.publicAccess());
        then(contentRepository).should()
            .save(ImageConstants.CHANGE_KEY, content);
        then(contentRepository).should()
            .delete(ImageConstants.KEY);
    }

    @Test
    @DisplayName("When old content deletion fails, the update still succeeds")
    void testUpdate_ContentDeletionFailureIsIgnored() {
        final Asset updated;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.publicAccess()));
        given(contentKeyGenerator.generate("images")).willReturn(ImageConstants.CHANGE_KEY);
        given(repository.save(any(Asset.class))).willReturn(Images.publicAccess());
        willThrow(new RuntimeException("S3 deletion failed")).given(contentRepository)
            .delete(ImageConstants.KEY);

        // WHEN
        updated = service.update(Images.publicAccess(), Contents.image());

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(Images.publicAccess());
    }

    @Test
    @DisplayName("When updating an image with an existing name, an exception is thrown")
    void testUpdate_DuplicateName() {
        final ThrowingCallable callable;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.publicAccess()));
        given(repository.existsByNameAndFolder(ImageConstants.NAME, null, ImageConstants.NUMBER)).willReturn(true);

        // WHEN
        callable = () -> service.update(Images.publicAccess(), Contents.image());

        // WHEN + THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(AssetAlreadyExistsException.class);
    }

    @Test
    @DisplayName("When update persistence fails, the replacement content is deleted")
    void testUpdate_PersistenceFailureDeletesReplacement() {
        final ThrowingCallable callable;
        final RuntimeException failure;

        // GIVEN
        failure = new RuntimeException("Persistence failed");
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.publicAccess()));
        given(contentKeyGenerator.generate("images")).willReturn(ImageConstants.CHANGE_KEY);
        willThrow(failure).given(repository)
            .save(any(Asset.class));

        // WHEN
        callable = () -> service.update(Images.publicAccess(), Contents.image());

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
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.publicAccess()));
        given(contentKeyGenerator.generate("images")).willReturn(ImageConstants.CHANGE_KEY);
        given(repository.save(any(Asset.class))).willReturn(Images.publicAccess());

        // WHEN
        service.update(Images.publicAccess(), Contents.image());

        // THEN
        then(repository).should()
            .save(Images.change());
    }

}
