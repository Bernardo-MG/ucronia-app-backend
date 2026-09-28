
package com.bernardomg.file.test.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;

import com.bernardomg.asset.adapter.inbound.jpa.repository.AssetFolderSpringRepository;
import com.bernardomg.asset.adapter.inbound.jpa.repository.AssetSpringRepository;
import com.bernardomg.file.adapter.inbound.jpa.repository.JpaFileFolderRepository;
import com.bernardomg.file.adapter.inbound.jpa.repository.JpaFileRepository;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.domain.repository.FileRepository;

@Configuration
@EnableJpaRepositories(basePackages = { "com.bernardomg.asset.adapter.inbound.jpa",
        "com.bernardomg.file.adapter.inbound.jpa", "com.bernardomg.security.adapter.inbound.jpa" })
@EntityScan(
        basePackages = { "com.bernardomg.security.adapter.inbound.jpa", "com.bernardomg.asset.adapter.inbound.jpa" })
public class TestConfiguration {

    @Bean("authenticationTrustResolver")
    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }

    @Bean("fileFolderRepository")
    public FileFolderRepository getFileFolderRepository(final AssetFolderSpringRepository repository) {
        return new JpaFileFolderRepository(repository);
    }

    @Bean("fileRepository")
    public FileRepository getFileRepository(final AssetSpringRepository repository,
            final AssetFolderSpringRepository folderRepository) {
        return new JpaFileRepository(repository, folderRepository);
    }
}
