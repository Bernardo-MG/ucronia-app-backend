
package com.bernardomg.image.test.adapter.s3.repository.unit;

import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import com.bernardomg.image.adapter.s3.repository.S3ImageContentRepository;
import com.bernardomg.image.domain.exception.ImageContentNotExistingException;
import com.bernardomg.image.domain.model.ImageContent;
import com.bernardomg.image.test.configuration.factory.ImageConstants;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@ExtendWith(MockitoExtension.class)
@DisplayName("S3 image content repository")
class TestS3ImageContentRepositoryGetOne {

    @Mock
    private S3Client                 client;

    private S3ImageContentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new S3ImageContentRepository(client, ImageConstants.BUCKET);
    }

    @Test
    @DisplayName("When getting image content, data and media type are returned")
    void testGetOne() {
        final ImageContent      content;
        final GetObjectRequest  request;
        final GetObjectResponse response;

        // GIVEN
        request = GetObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.KEY)
            .build();
        response = GetObjectResponse.builder()
            .contentType(ImageConstants.PNG_MEDIA_TYPE)
            .build();
        given(client.getObjectAsBytes(request)).willReturn(ResponseBytes.fromByteArray(response, ImageConstants.DATA));

        // WHEN
        content = repository.getOne(ImageConstants.KEY);

        // THEN
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(content.data())
                .containsExactly(ImageConstants.DATA);
            soft.assertThat(content.mediaType())
                .isEqualTo(ImageConstants.PNG_MEDIA_TYPE);
        });
    }

    @Test
    @DisplayName("When getting missing image content, not found is raised")
    void testGetOne_Missing() {
        final GetObjectRequest request;
        final ThrowingCallable callable;

        // GIVEN
        request = GetObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.KEY)
            .build();
        given(client.getObjectAsBytes(request)).willThrow(NoSuchKeyException.builder()
            .build());

        // WHEN
        callable = () -> repository.getOne(ImageConstants.KEY);

        // THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(ImageContentNotExistingException.class);
    }

    @Test
    @DisplayName("When getting image content without a media type, the default media type is returned")
    void testGetOne_WithoutMediaType() {
        final ImageContent      content;
        final GetObjectRequest  request;
        final GetObjectResponse response;

        // GIVEN
        request = GetObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.KEY)
            .build();
        response = GetObjectResponse.builder()
            .build();
        given(client.getObjectAsBytes(request)).willReturn(ResponseBytes.fromByteArray(response, ImageConstants.DATA));

        // WHEN
        content = repository.getOne(ImageConstants.KEY);

        // THEN
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(content.data())
                .containsExactly(ImageConstants.DATA);
            soft.assertThat(content.mediaType())
                .isEqualTo(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        });
    }

}
