/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.asset.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bernardomg.asset.adapter.outbound.rest.security.SpringSecurityAssetReadAuthorizer;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.test.configuration.factory.AssetConstants;
import com.bernardomg.asset.test.configuration.factory.AssetFolderConstants;
import com.bernardomg.asset.test.configuration.factory.AssetFolders;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.usecase.service.AssetFolderService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;

@ExtendWith(MockitoExtension.class)
@DisplayName("AssetFolderController")
class TestAssetFolderController {

    private MockMvc                     mockMvc;

    @Mock
    private ResourcePermissionEvaluator permissionEvaluator;

    @Mock
    private AssetFolderService          service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(
                new AssetFolderController(service, new SpringSecurityAssetReadAuthorizer(permissionEvaluator)))
            .build();
    }

    @Test
    @DisplayName("Can get all asset folders")
    void testGetAssetFolders() throws Exception {
        given(service.getAll()).willReturn(List.of(AssetFolders.valid()));

        mockMvc.perform(get("/asset/folders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].number").value(AssetFolderConstants.NUMBER))
            .andExpect(jsonPath("$[0].name").value(AssetFolderConstants.NAME));
    }

    @Test
    @DisplayName("Root asset responses include their type")
    void testGetRootAssets_Type() throws Exception {
        final Page<Asset> page;

        page = new Page<>(List.of(Assets.publicAccess()), 10, 1, 1, 1, 1, true, true, Sorting.unsorted());
        given(service.getPublicRootAssets(any(Pagination.class), any(Sorting.class))).willReturn(page);

        mockMvc.perform(get("/asset/folders/root/assets").param("page", "1")
            .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].number").value(AssetConstants.NUMBER))
            .andExpect(jsonPath("$.content[0].type").value("FILE"));
    }

}
