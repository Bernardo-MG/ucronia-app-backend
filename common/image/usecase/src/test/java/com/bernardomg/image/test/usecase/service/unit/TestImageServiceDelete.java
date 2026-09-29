
package com.bernardomg.image.test.usecase.service.unit;

import static com.bernardomg.asset.domain.model.AssetType.IMAGE;
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

import com.bernardomg.asset.domain.exception.AssetNotExistingException;
import com.bernardomg.asset.domain.key.ContentKeyGenerator;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.policy.ContentPolicy;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.image.usecase.service.DefaultImageService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Asset service - delete")
class TestImageServiceDelete {

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
    @DisplayName("When deleting an image, the image is deleted")
    void testDelete() {
        // GIVEN
        given(repository.findOne(IMAGE, ImageConstants.NUMBER)).willReturn(Optional.of(Images.publicAccess()));

        // WHEN
        service.delete(ImageConstants.NUMBER);

        // THEN
        then(contentRepository).should()
            .delete(ImageConstants.KEY);
    }

    @Test
    @DisplayName("When content deletion fails, the metadata deletion still succeeds")
    void testDelete_ContentDeletionFailureIsIgnored() {

        // GIVEN
        given(repository.findOne(IMAGE, ImageConstants.NUMBER)).willReturn(Optional.of(Images.publicAccess()));
        willThrow(new RuntimeException("S3 deletion failed")).given(contentRepository)
            .delete(ImageConstants.KEY);

        // WHEN
        service.delete(ImageConstants.NUMBER);

        // THEN
        then(repository).should()
            .delete(IMAGE, ImageConstants.NUMBER);
    }

    @Test
    @DisplayName("When metadata deletion fails, the content is preserved")
    void testDelete_MetadataDeletionFailurePreservesContent() {
        final RuntimeException exception;
        final ThrowingCallable execution;

        // GIVEN
        exception = new RuntimeException("Database deletion failed");
        given(repository.findOne(IMAGE, ImageConstants.NUMBER)).willReturn(Optional.of(Images.publicAccess()));
        willThrow(exception).given(repository)
            .delete(IMAGE, ImageConstants.NUMBER);

        // WHEN
        execution = () -> service.delete(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isSameAs(exception);
        then(contentRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("When deleting a missing image, not found is raised")
    void testDelete_Missing() {
        final ThrowingCallable callable;

        // GIVEN
        given(repository.findOne(IMAGE, ImageConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        callable = () -> service.delete(ImageConstants.NUMBER);

        // WHEN + THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(AssetNotExistingException.class);
    }

    @Test
    @DisplayName("When deleting an image, the deleted image is returned")
    void testDelete_Returned() {
        final Asset deleted;

        // GIVEN
        given(repository.findOne(IMAGE, ImageConstants.NUMBER)).willReturn(Optional.of(Images.publicAccess()));

        // WHEN
        deleted = service.delete(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(deleted)
            .isEqualTo(Images.publicAccess());
    }

}
