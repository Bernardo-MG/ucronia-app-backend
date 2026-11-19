
package com.bernardomg.asset.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.adapter.outbound.rest.security.AssetReadAuthorizer;
import com.bernardomg.asset.test.configuration.factory.AssetConstants;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.usecase.service.AssetFolderService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("AssetFolderController - getRootAssets")
class TestAssetFolderControllerGetRootAssets {

    @InjectMocks
    private AssetFolderController controller;

    private MockMvc                     mockMvc;

    @Mock
    private AssetReadAuthorizer authorizer;

    @Mock
    private AssetFolderService          service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator;

        validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When a root asset is requested, then its response includes their type")
    void testGetRootAssets_Type() throws Exception {
        final Page<Asset> page;

        // GIVEN
        page = new Page<>(List.of(Assets.publicAccess()), 10, 1, 1, 1, 1, true, true, Sorting.unsorted());
        given(service.getPublicRootAssets(any(Pagination.class), any(Sorting.class))).willReturn(page);

        // WHEN
        mockMvc.perform(get("/assets/folders/root/assets").param("page", "1")
            .param("size", "10"))
            // THEN
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].number").value(AssetConstants.NUMBER))
            .andExpect(jsonPath("$.content[0].type").value("FILE"));
    }

}
