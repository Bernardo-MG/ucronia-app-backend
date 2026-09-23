
package com.bernardomg.content.test.adapter.s3.repository.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.content.adapter.s3.repository.S3ContentRepository;
import com.bernardomg.content.domain.model.Content;
import com.bernardomg.content.test.adapter.s3.config.factory.ContentConstants;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@ExtendWith(MockitoExtension.class)
@DisplayName("S3 image content repository")
class TestS3ContentRepositorySave {

    @Mock
    private S3Client            client;

    private S3ContentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new S3ContentRepository(client, ContentConstants.BUCKET);
    }

    @Test
    @DisplayName("When saving image content, data and media type are persisted")
    void testSave() {
        final Content          content;
        final PutObjectRequest request;

        // GIVEN
        content = new Content(new ByteArrayInputStream(ContentConstants.DATA), ContentConstants.DATA.length,
            ContentConstants.MEDIA_TYPE);
        request = PutObjectRequest.builder()
            .bucket(ContentConstants.BUCKET)
            .key(ContentConstants.KEY)
            .contentType(ContentConstants.MEDIA_TYPE)
            .build();

        // WHEN
        repository.save(ContentConstants.KEY, content);

        // THEN
        verify(client).putObject(eq(request), any(RequestBody.class));
    }

}
