
package com.bernardomg.image.test.adapter.s3.repository.unit;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.image.adapter.s3.repository.S3ImageContentRepository;
import com.bernardomg.image.test.configuration.factory.ImageConstants;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@ExtendWith(MockitoExtension.class)
@DisplayName("S3 image content repository")
class TestS3ImageContentRepositoryDelete {

    @Mock
    private S3Client                 client;

    private S3ImageContentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new S3ImageContentRepository(client, ImageConstants.BUCKET);
    }

    @Test
    @DisplayName("When deleting image content, it is deleted from S3")
    void testDelete() {
        // WHEN
        repository.delete(ImageConstants.KEY);

        // THEN
        verify(client).deleteObject(DeleteObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.KEY)
            .build());
    }

}
