
package com.bernardomg.content.test.adapter.s3.repository.unit;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.content.adapter.s3.repository.S3ContentRepository;
import com.bernardomg.content.test.adapter.s3.config.factory.ContentConstants;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@ExtendWith(MockitoExtension.class)
@DisplayName("S3 image content repository")
class TestS3ContentRepositoryDelete {

    @Mock
    private S3Client            client;

    private S3ContentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new S3ContentRepository(client, ContentConstants.BUCKET);
    }

    @Test
    @DisplayName("When deleting image content, it is deleted from S3")
    void testDelete() {
        // WHEN
        repository.delete(ContentConstants.KEY);

        // THEN
        verify(client).deleteObject(DeleteObjectRequest.builder()
            .bucket(ContentConstants.BUCKET)
            .key(ContentConstants.KEY)
            .build());
    }

}
