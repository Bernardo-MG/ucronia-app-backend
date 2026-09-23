
package com.bernardomg.content.adapter.s3.repository;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.bernardomg.content.domain.exception.ContentNotExistingException;
import com.bernardomg.content.domain.model.Content;
import com.bernardomg.content.domain.repository.ContentRepository;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public final class S3ContentRepository implements ContentRepository {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(S3ContentRepository.class);

    private final String        bucket;

    private final S3Client      client;

    public S3ContentRepository(final S3Client s3Client, final String s3Bucket) {
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
    public final Content getOne(final String key) {
        final GetObjectRequest                       request;
        final ResponseInputStream<GetObjectResponse> response;
        final String                                 mediaType;

        request = GetObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build();
        try {
            response = client.getObject(request);
        } catch (final NoSuchKeyException ex) {
            log.error("Content {} doesn't exist", key);
            throw new ContentNotExistingException(key);
        }

        mediaType = response.response()
            .contentType();
        return new Content(response, response.response()
            .contentLength(), mediaType != null ? mediaType : MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    @Override
    public final void save(final String key, final Content content) {
        final PutObjectRequest request;

        request = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(content.mediaType())
            .build();
        client.putObject(request, RequestBody.fromInputStream(content.data(), content.size()));
    }

}
