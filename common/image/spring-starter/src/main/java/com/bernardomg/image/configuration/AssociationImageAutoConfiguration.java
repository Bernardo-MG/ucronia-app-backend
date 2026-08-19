/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.image.configuration;

import java.net.URI;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;

import com.bernardomg.image.usecase.service.DefaultImageService;
import com.bernardomg.image.usecase.service.ImageService;
import com.bernardomg.security.springframework.web.whitelist.WhitelistRoute;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@AutoConfiguration
@ComponentScan({ "com.bernardomg.image.adapter.outbound.rest.controller" })
@EnableConfigurationProperties(ImageS3Properties.class)
public class AssociationImageAutoConfiguration {

    @Bean("imageService")
    public ImageService getImageService(final S3Client s3Client, final ImageS3Properties properties) {
        return new DefaultImageService(s3Client, properties.getBucket());
    }

    @Bean("imageWhitelist")
    public WhitelistRoute getImageWhitelist() {
        return WhitelistRoute.of("/images/**", HttpMethod.GET);
    }

    @Bean
    public S3Client getS3Client(final ImageS3Properties properties) {
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
