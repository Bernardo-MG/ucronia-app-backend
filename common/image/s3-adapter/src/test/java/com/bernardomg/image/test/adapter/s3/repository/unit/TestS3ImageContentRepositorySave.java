
package com.bernardomg.image.test.adapter.s3.repository.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.image.adapter.s3.repository.S3ImageContentRepository;
import com.bernardomg.image.domain.model.ImageContent;
import com.bernardomg.image.test.configuration.factory.ImageConstants;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@ExtendWith(MockitoExtension.class)
@DisplayName("S3 image content repository")
class TestS3ImageContentRepositorySave {

    @Mock
    private S3Client                 client;

    private S3ImageContentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new S3ImageContentRepository(client, ImageConstants.BUCKET);
    }

    @Test
    @DisplayName("When saving image content, data and media type are persisted")
    void testSave() {
        final ImageContent     content;
        final PutObjectRequest request;

        // GIVEN
        content = new ImageContent(ImageConstants.DATA, ImageConstants.PNG_MEDIA_TYPE);
        request = PutObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.KEY)
            .contentType(ImageConstants.PNG_MEDIA_TYPE)
            .build();

        // WHEN
        repository.save(ImageConstants.KEY, content);

        // THEN
        verify(client).putObject(eq(request), any(RequestBody.class));
    }

}
