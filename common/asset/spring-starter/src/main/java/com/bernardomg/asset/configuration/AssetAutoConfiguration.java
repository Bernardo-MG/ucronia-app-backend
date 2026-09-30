
package com.bernardomg.asset.configuration;

import java.net.URI;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;

import com.bernardomg.asset.adapter.inbound.jpa.repository.AssetFolderSpringRepository;
import com.bernardomg.asset.adapter.inbound.jpa.repository.AssetSpringRepository;
import com.bernardomg.asset.adapter.inbound.jpa.repository.JpaAssetFolderRepository;
import com.bernardomg.asset.adapter.inbound.jpa.repository.JpaAssetRepository;
import com.bernardomg.asset.adapter.outbound.rest.security.AssetReadAuthorizer;
import com.bernardomg.asset.adapter.outbound.rest.security.SpringSecurityAssetReadAuthorizer;
import com.bernardomg.asset.adapter.outbound.s3.repository.S3ContentRepository;
import com.bernardomg.asset.domain.key.ContentKeyGenerator;
import com.bernardomg.asset.domain.key.UuidContentKeyGenerator;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.asset.usecase.service.AssetContentService;
import com.bernardomg.asset.usecase.service.AssetFolderService;
import com.bernardomg.asset.usecase.service.DefaultAssetContentService;
import com.bernardomg.asset.usecase.service.DefaultAssetFolderService;
import com.bernardomg.security.springframework.access.interceptor.AuthorityResourcePermissionEvaluator;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;
import com.bernardomg.security.springframework.web.whitelist.WhitelistRoute;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@AutoConfiguration
@ComponentScan({ "com.bernardomg.asset.adapter.outbound.rest.controller" })
@AutoConfigurationPackage(basePackages = "com.bernardomg.asset.adapter.inbound.jpa")
@EnableConfigurationProperties(ContentStorageProperties.class)
public class AssetAutoConfiguration {

    @Bean("assetContentService")
    public AssetContentService getAssetContentService(final AssetRepository assetRepository,
            final ContentRepository contentRepository) {
        return new DefaultAssetContentService(assetRepository, contentRepository);
    }

    @Bean("assetContentWhitelist")
    public WhitelistRoute getAssetContentWhitelist() {
        return WhitelistRoute.of("/assets/**", HttpMethod.GET);
    }

    @Bean("assetFolderRepository")
    public AssetFolderRepository getAssetFolderRepository(final AssetFolderSpringRepository repository) {
        return new JpaAssetFolderRepository(repository);
    }

    @Bean("assetFolderService")
    public AssetFolderService getAssetFolderService(final AssetFolderRepository folderRepository,
            final AssetRepository assetRepository) {
        return new DefaultAssetFolderService(folderRepository, assetRepository);
    }

    @Bean("assetFolderWhitelist")
    public WhitelistRoute getAssetFolderWhitelist() {
        return WhitelistRoute.of("/asset-folders/**", HttpMethod.GET);
    }

    @Bean("assetReadAuthorizer")
    public AssetReadAuthorizer getAssetReadAuthorizer() {
        final ResourcePermissionEvaluator permissionEvaluator;

        permissionEvaluator = new AuthorityResourcePermissionEvaluator();
        return new SpringSecurityAssetReadAuthorizer(permissionEvaluator);
    }

    @Bean("assetRepository")
    public AssetRepository getAssetRepository(final AssetSpringRepository repository,
            final AssetFolderSpringRepository folderRepository) {
        return new JpaAssetRepository(repository, folderRepository);
    }

    @Bean
    public ContentKeyGenerator getContentKeyGenerator() {
        return new UuidContentKeyGenerator();
    }

    @Bean
    public ContentRepository getContentRepository(final S3Client s3Client, final ContentStorageProperties properties) {
        return new S3ContentRepository(s3Client, properties.getBucket());
    }

    @Bean
    public S3Client getS3Client(final ContentStorageProperties properties) {
        final S3ClientBuilder builder;

        builder = S3Client.builder()
            .region(Region.of(properties.getRegion()))
            .forcePathStyle(properties.isPathStyle());
        if ((properties.getEndpoint() != null) && !properties.getEndpoint()
            .isBlank()) {
            builder.endpointOverride(URI.create(properties.getEndpoint()));
        }
        if ((properties.getAccessKey() != null) && !properties.getAccessKey()
            .isBlank()) {
            builder.credentialsProvider(StaticCredentialsProvider
                .create(AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey())));
        }
        return builder.build();
    }

}
