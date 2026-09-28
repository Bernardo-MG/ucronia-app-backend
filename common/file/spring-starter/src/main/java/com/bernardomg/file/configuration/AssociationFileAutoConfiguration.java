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

package com.bernardomg.file.configuration;

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
import com.bernardomg.file.adapter.inbound.jpa.repository.FileFolderSpringRepository;
import com.bernardomg.file.adapter.inbound.jpa.repository.FileSpringRepository;
import com.bernardomg.file.adapter.inbound.jpa.repository.JpaFileFolderRepository;
import com.bernardomg.file.adapter.inbound.jpa.repository.JpaFileRepository;
import com.bernardomg.file.adapter.outbound.rest.security.FileReadAuthorizer;
import com.bernardomg.file.adapter.outbound.rest.security.SpringSecurityFileReadAuthorizer;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.domain.repository.FileRepository;
import com.bernardomg.file.usecase.service.DefaultFileFolderService;
import com.bernardomg.file.usecase.service.DefaultFileService;
import com.bernardomg.file.usecase.service.FileFolderService;
import com.bernardomg.file.usecase.service.FileService;
import com.bernardomg.security.springframework.access.interceptor.AuthorityResourcePermissionEvaluator;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;
import com.bernardomg.security.springframework.web.whitelist.WhitelistRoute;

@AutoConfiguration
@ComponentScan({ "com.bernardomg.file.adapter.outbound.rest.controller" })
@AutoConfigurationPackage(
        basePackages = { "com.bernardomg.asset.adapter.inbound.jpa", "com.bernardomg.file.adapter.inbound.jpa" })
@EnableConfigurationProperties(FileContentProperties.class)
public class AssociationFileAutoConfiguration {

    @Bean("fileContentPolicy")
    public ContentPolicy getContentPolicy(final FileContentProperties properties) {
        return new RestrictedContentPolicy(properties.getMaximumSize()
            .toBytes(), properties.getAllowedMediaTypes());
    }

    @Bean("fileFolderRepository")
    public FileFolderRepository getFileFolderRepository(final FileFolderSpringRepository repository) {
        return new JpaFileFolderRepository(repository);
    }

    @Bean("fileFolderService")
    public FileFolderService getFileFolderService(final FileFolderRepository folderRepository,
            final FileRepository fileRepository) {
        return new DefaultFileFolderService(folderRepository, fileRepository);
    }

    @Bean("fileFolderWhitelist")
    public WhitelistRoute getFileFolderWhitelist() {
        return WhitelistRoute.of("/file-folders/**", HttpMethod.GET);
    }

    @Bean("fileReadAuthorizer")
    public FileReadAuthorizer getFileReadAuthorizer() {
        final ResourcePermissionEvaluator permissionEvaluator;

        permissionEvaluator = new AuthorityResourcePermissionEvaluator();
        return new SpringSecurityFileReadAuthorizer(permissionEvaluator);
    }

    @Bean("fileRepository")
    public FileRepository getFileRepository(final FileSpringRepository repository,
            final FileFolderSpringRepository folderRepository) {
        return new JpaFileRepository(repository, folderRepository);
    }

    @Bean("fileService")
    public FileService getFileService(final FileRepository fileRepository, final ContentRepository contentRepository,
            @Qualifier("fileContentPolicy") final ContentPolicy fileContentPolicy,
            final ContentKeyGenerator contentKeyGenerator) {
        return new DefaultFileService(fileRepository, contentRepository, fileContentPolicy, contentKeyGenerator);
    }

    @Bean("fileWhitelist")
    public WhitelistRoute getFileWhitelist() {
        return WhitelistRoute.of("/files/**", HttpMethod.GET);
    }

}
