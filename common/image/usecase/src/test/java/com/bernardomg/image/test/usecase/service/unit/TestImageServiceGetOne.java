
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

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.content.domain.key.ContentKeyGenerator;
import com.bernardomg.content.domain.policy.ContentPolicy;
import com.bernardomg.content.domain.repository.ContentRepository;
import com.bernardomg.image.domain.exception.ImageNotExistingException;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.image.usecase.service.DefaultImageService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Asset service")
class TestImageServiceGetOne {

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
    @DisplayName("When getting an image, its metadata is returned")
    void testGetOne() {
        final Asset result;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(Images.publicAccess()));

        // WHEN
        result = service.getOne(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(result)
            .isEqualTo(Images.publicAccess());
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
