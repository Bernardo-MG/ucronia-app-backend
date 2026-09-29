
package com.bernardomg.asset.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Bean;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetType;
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

    @Bean("fileFolderRepository")
    public AssetFolderRepository getFileFolderRepository(final AssetFolderSpringRepository repository) {
        return new JpaAssetFolderRepository(AssetType.FILE, repository);
    }

    @Bean("fileFolderService")
    public AssetFolderService getFileFolderService(
            @Qualifier("fileFolderRepository") final AssetFolderRepository folderRepository,
            @Qualifier("fileRepository") final AssetRepository assetRepository) {
        return new DefaultAssetFolderService(folderRepository, assetRepository);
    }

    @Bean("fileRepository")
    public AssetRepository getFileRepository(final AssetSpringRepository repository,
            final AssetFolderSpringRepository folderRepository) {
        return new JpaAssetRepository(AssetType.FILE, repository, folderRepository);
    }

    @Bean("imageFolderRepository")
    public AssetFolderRepository getImageFolderRepository(final AssetFolderSpringRepository repository) {
        return new JpaAssetFolderRepository(AssetType.IMAGE, repository);
    }

    @Bean("imageFolderService")
    public AssetFolderService getImageFolderService(
            @Qualifier("imageFolderRepository") final AssetFolderRepository folderRepository,
            @Qualifier("imageRepository") final AssetRepository assetRepository) {
        return new DefaultAssetFolderService(folderRepository, assetRepository);
    }

    @Bean("imageRepository")
    public AssetRepository getImageRepository(final AssetSpringRepository repository,
            final AssetFolderSpringRepository folderRepository) {
        return new JpaAssetRepository(AssetType.IMAGE, repository, folderRepository);
    }

}
