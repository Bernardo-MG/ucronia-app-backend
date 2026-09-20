
package com.bernardomg.image.adapter.s3.repository;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.bernardomg.image.domain.exception.ImageContentNotExistingException;
import com.bernardomg.image.domain.model.ImageContent;
import com.bernardomg.image.domain.repository.ImageContentRepository;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public final class S3ImageContentRepository implements ImageContentRepository {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(S3ImageContentRepository.class);

    private final String        bucket;

    private final S3Client      client;

    public S3ImageContentRepository(final S3Client s3Client, final String s3Bucket) {
        super();

        client = Objects.requireNonNull(s3Client);
        bucket = Objects.requireNonNull(s3Bucket);
    }

    @Override
    public final void delete(final String key) {
        final DeleteObjectRequest request;

        request = DeleteObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build();
        client.deleteObject(request);
    }

    @Override
    public final ImageContent getOne(final String key) {
        final GetObjectRequest                 request;
        final ResponseBytes<GetObjectResponse> response;
        final String                           mediaType;

        request = GetObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build();
        try {
            response = client.getObjectAsBytes(request);
        } catch (final NoSuchKeyException ex) {
            log.error("Image {} doesn't exist", key);
            throw new ImageContentNotExistingException(key);
        }

        mediaType = response.response()
            .contentType();
        return new ImageContent(response.asByteArray(),
            mediaType != null ? mediaType : MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    @Override
    public final void save(final String name, final ImageContent content) {
        final PutObjectRequest request;

        request = PutObjectRequest.builder()
            .bucket(bucket)
            .key(name)
            .contentType(content.mediaType())
            .build();
        client.putObject(request, RequestBody.fromBytes(content.data()));
    }

}
