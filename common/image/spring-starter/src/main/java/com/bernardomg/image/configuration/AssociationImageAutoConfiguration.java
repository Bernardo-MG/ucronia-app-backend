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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;

import com.bernardomg.content.domain.key.ContentKeyGenerator;
import com.bernardomg.content.domain.policy.ContentPolicy;
import com.bernardomg.content.domain.policy.RestrictedContentPolicy;
import com.bernardomg.content.domain.repository.ContentRepository;
import com.bernardomg.image.adapter.inbound.jpa.repository.ImageFolderSpringRepository;
import com.bernardomg.image.adapter.inbound.jpa.repository.ImageSpringRepository;
import com.bernardomg.image.adapter.inbound.jpa.repository.JpaImageFolderRepository;
import com.bernardomg.image.adapter.inbound.jpa.repository.JpaImageRepository;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.usecase.service.DefaultImageFolderService;
import com.bernardomg.image.usecase.service.DefaultImageService;
import com.bernardomg.image.usecase.service.ImageFolderService;
import com.bernardomg.image.usecase.service.ImageService;
import com.bernardomg.security.springframework.web.whitelist.WhitelistRoute;

@AutoConfiguration
@ComponentScan({ "com.bernardomg.image.adapter.outbound.rest.controller" })
@AutoConfigurationPackage(basePackages = { "com.bernardomg.image.adapter.inbound.jpa" })
@EnableConfigurationProperties(ImageContentProperties.class)
public class AssociationImageAutoConfiguration {

    @Bean("imageContentPolicy")
    public ContentPolicy getContentPolicy(final ImageContentProperties properties) {
        return new RestrictedContentPolicy(properties.getMaximumSize()
            .toBytes(), properties.getAllowedMediaTypes());
    }

    @Bean("imageFolderRepository")
    public ImageFolderRepository getImageFolderRepository(final ImageFolderSpringRepository repository) {
        return new JpaImageFolderRepository(repository);
    }

    @Bean("imageFolderService")
    public ImageFolderService getImageFolderService(final ImageFolderRepository folderRepository,
            final ImageRepository imageRepository) {
        return new DefaultImageFolderService(folderRepository, imageRepository);
    }

    @Bean("imageFolderWhitelist")
    public WhitelistRoute getImageFolderWhitelist() {
        return WhitelistRoute.of("/image-folders/**", HttpMethod.GET);
    }

    @Bean("imageRepository")
    public ImageRepository getImageRepository(final ImageSpringRepository repository,
            final ImageFolderSpringRepository folderRepository) {
        return new JpaImageRepository(repository, folderRepository);
    }

    @Bean("imageService")
    public ImageService getImageService(final ImageRepository imageRepository,
            final ContentRepository contentRepository,
            @Qualifier("imageContentPolicy") final ContentPolicy imageContentPolicy,
            final ContentKeyGenerator contentKeyGenerator) {
        return new DefaultImageService(imageRepository, contentRepository, imageContentPolicy, contentKeyGenerator);
    }

    @Bean("imageWhitelist")
    public WhitelistRoute getImageWhitelist() {
        return WhitelistRoute.of("/images/**", HttpMethod.GET);
    }

}
