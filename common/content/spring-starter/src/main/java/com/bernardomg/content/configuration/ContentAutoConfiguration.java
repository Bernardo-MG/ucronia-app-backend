package com.bernardomg.content.configuration;

import java.net.URI;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.bernardomg.content.adapter.s3.repository.S3ContentRepository;
import com.bernardomg.content.domain.key.ContentKeyGenerator;
import com.bernardomg.content.domain.key.UuidContentKeyGenerator;
import com.bernardomg.content.domain.repository.ContentRepository;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@AutoConfiguration
@ComponentScan("com.bernardomg.content.adapter.rest")
@EnableConfigurationProperties(ContentStorageProperties.class)
public class ContentAutoConfiguration {

    @Bean
    public ContentKeyGenerator getContentKeyGenerator() {
        return new UuidContentKeyGenerator();
    }

    @Bean
    public ContentRepository getContentRepository(final S3Client s3Client,
            final ContentStorageProperties properties) {
        return new S3ContentRepository(s3Client, properties.getBucket());
    }

    @Bean
    public S3Client getS3Client(final ContentStorageProperties properties) {
        final S3ClientBuilder builder;

        builder = S3Client.builder()
            .region(Region.of(properties.getRegion()))
            .forcePathStyle(properties.isPathStyle());
        if ((properties.getEndpoint() != null) && !properties.getEndpoint().isBlank()) {
            builder.endpointOverride(URI.create(properties.getEndpoint()));
        }
        if ((properties.getAccessKey() != null) && !properties.getAccessKey().isBlank()) {
            builder.credentialsProvider(StaticCredentialsProvider
                .create(AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey())));
        }
        return builder.build();
    }

}
