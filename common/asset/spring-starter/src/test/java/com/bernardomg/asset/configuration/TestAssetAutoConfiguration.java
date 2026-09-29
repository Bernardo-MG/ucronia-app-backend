/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.asset.configuration;

import static org.mockito.Mockito.mock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import com.bernardomg.asset.adapter.inbound.jpa.repository.AssetFolderSpringRepository;
import com.bernardomg.asset.adapter.inbound.jpa.repository.AssetSpringRepository;
import com.bernardomg.asset.adapter.outbound.rest.controller.AssetFolderController;
import com.bernardomg.asset.domain.key.ContentKeyGenerator;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.asset.usecase.service.AssetFolderService;

@DisplayName("AssetAutoConfiguration")
class TestAssetAutoConfiguration {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(AssetAutoConfiguration.class))
        .withBean(AssetSpringRepository.class, () -> mock(AssetSpringRepository.class))
        .withBean(AssetFolderSpringRepository.class, () -> mock(AssetFolderSpringRepository.class))
        .withPropertyValues("content.storage.region=eu-west-1", "content.storage.bucket=test");

    @Test
    @DisplayName("It creates one shared asset folder controller")
    void testAssetFolderController() {
        contextRunner.run(context -> Assertions.assertThat(context)
            .hasSingleBean(AssetFolderController.class));
    }

    @Test
    @DisplayName("It creates one shared asset folder repository")
    void testAssetFolderRepository() {
        contextRunner.run(context -> Assertions.assertThat(context)
            .hasSingleBean(AssetFolderRepository.class));
    }

    @Test
    @DisplayName("It creates one shared asset folder service")
    void testAssetFolderService() {
        contextRunner.run(context -> Assertions.assertThat(context)
            .hasSingleBean(AssetFolderService.class));
    }

    @Test
    @DisplayName("It creates one shared asset repository")
    void testAssetRepository() {
        contextRunner.run(context -> Assertions.assertThat(context)
            .hasSingleBean(AssetRepository.class));
    }

    @Test
    @DisplayName("It creates one content key generator")
    void testContentKeyGenerator() {
        contextRunner.run(context -> Assertions.assertThat(context)
            .hasSingleBean(ContentKeyGenerator.class));
    }

    @Test
    @DisplayName("It creates one content repository")
    void testContentRepository() {
        contextRunner.run(context -> Assertions.assertThat(context)
            .hasSingleBean(ContentRepository.class));
    }

}
