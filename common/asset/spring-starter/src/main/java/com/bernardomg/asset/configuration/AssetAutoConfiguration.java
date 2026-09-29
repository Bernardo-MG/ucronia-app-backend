
package com.bernardomg.asset.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Bean;

import com.bernardomg.asset.adapter.inbound.jpa.repository.AssetFolderSpringRepository;
import com.bernardomg.asset.adapter.inbound.jpa.repository.AssetSpringRepository;
import com.bernardomg.asset.adapter.inbound.jpa.repository.JpaAssetFolderRepository;
import com.bernardomg.asset.adapter.inbound.jpa.repository.JpaAssetRepository;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.usecase.service.AssetFolderService;
import com.bernardomg.asset.usecase.service.DefaultAssetFolderService;

@AutoConfiguration
@AutoConfigurationPackage(basePackages = "com.bernardomg.asset.adapter.inbound.jpa")
public class AssetAutoConfiguration {

    @Bean("assetFolderRepository")
    public AssetFolderRepository getAssetFolderRepository(final AssetFolderSpringRepository repository) {
        return new JpaAssetFolderRepository(repository);
    }

    @Bean("assetFolderService")
    public AssetFolderService getAssetFolderService(final AssetFolderRepository folderRepository,
            final AssetRepository assetRepository) {
        return new DefaultAssetFolderService(folderRepository, assetRepository);
    }

    @Bean("assetRepository")
    public AssetRepository getAssetRepository(final AssetSpringRepository repository,
            final AssetFolderSpringRepository folderRepository) {
        return new JpaAssetRepository(repository, folderRepository);
    }

}
