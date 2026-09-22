
package com.bernardomg.image.test.adapter.s3.repository.unit;

import static org.mockito.BDDMockito.given;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.http.AbortableInputStream;
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
    void testGetOne() throws IOException {
        final ImageContent                           content;
        final GetObjectRequest                       request;
        final GetObjectResponse                      response;
        final ResponseInputStream<GetObjectResponse> responseStream;

        // GIVEN
        request = GetObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.KEY)
            .build();
        response = GetObjectResponse.builder()
            .contentType(ImageConstants.PNG_MEDIA_TYPE)
            .contentLength((long) ImageConstants.DATA.length)
            .build();
        responseStream = new ResponseInputStream<>(response,
            AbortableInputStream.create(new ByteArrayInputStream(ImageConstants.DATA)));
        given(client.getObject(request)).willReturn(responseStream);

        // WHEN
        content = repository.getOne(ImageConstants.KEY);

        // THEN
        SoftAssertions.assertSoftly(soft -> {
            try {
                soft.assertThat(content.data()
                    .readAllBytes())
                    .containsExactly(ImageConstants.DATA);
            } catch (final IOException e) {
                soft.fail();
            }
            soft.assertThat(content.size())
                .isEqualTo(ImageConstants.DATA.length);
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
        given(client.getObject(request)).willThrow(NoSuchKeyException.builder()
            .build());

        // WHEN
        callable = () -> repository.getOne(ImageConstants.KEY);

        // THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(ImageContentNotExistingException.class);
    }

    @Test
    @DisplayName("When getting image content without a media type, the default media type is returned")
    void testGetOne_WithoutMediaType() throws IOException {
        final ImageContent                           content;
        final GetObjectRequest                       request;
        final GetObjectResponse                      response;
        final ResponseInputStream<GetObjectResponse> responseStream;

        // GIVEN
        request = GetObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.KEY)
            .build();
        response = GetObjectResponse.builder()
            .contentLength((long) ImageConstants.DATA.length)
            .build();
        responseStream = new ResponseInputStream<>(response,
            AbortableInputStream.create(new ByteArrayInputStream(ImageConstants.DATA)));
        given(client.getObject(request)).willReturn(responseStream);

        // WHEN
        content = repository.getOne(ImageConstants.KEY);

        // THEN
        SoftAssertions.assertSoftly(soft -> {
            try {
                soft.assertThat(content.data()
                    .readAllBytes())
                    .containsExactly(ImageConstants.DATA);
            } catch (final IOException e) {
                soft.fail();
            }
            soft.assertThat(content.mediaType())
                .isEqualTo(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        });
    }

}
