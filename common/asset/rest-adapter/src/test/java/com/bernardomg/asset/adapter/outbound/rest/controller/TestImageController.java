
package com.bernardomg.asset.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.asset.adapter.outbound.rest.security.SpringSecurityAssetReadAuthorizer;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.asset.test.configuration.factory.AssetConstants;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.usecase.service.AssetService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageController")
class TestImageController {

    private MockMvc                     mockMvc;

    @Mock
    private ResourcePermissionEvaluator permissionEvaluator;

    @Mock
    private AssetService                service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator;

        validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
            .standaloneSetup(new ImageController(service, new SpringSecurityAssetReadAuthorizer(permissionEvaluator)))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("Can create an image")
    void testCreateImage() throws Exception {
        final MockMultipartFile file;

        // GIVEN
        file = new MockMultipartFile("file", AssetConstants.NAME, MediaType.IMAGE_PNG_VALUE, AssetConstants.DATA);
        given(service.create(any(Asset.class), any(Content.class))).willReturn(Assets.publicAccess());

        // WHEN + THEN
        mockMvc.perform(multipart("/images").file(file)
            .param("name", AssetConstants.NAME)
            .param("description", AssetConstants.DESCRIPTION)
            .param("public", "true"))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Can get all the images")
    void testGetAllAssets() throws Exception {
        final Page<Asset> page;

        // GIVEN
        page = new Page<>(List.of(Assets.publicAccess()), 10, 1, 1, 1, 1, true, true, Sorting.unsorted());
        given(service.getAllPublic(any(Pagination.class), any(Sorting.class))).willReturn(page);

        // WHEN + THEN
        mockMvc.perform(get("/images").param("page", "1")
            .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].number").value(AssetConstants.NUMBER))
            .andExpect(jsonPath("$.content[0].name").value(AssetConstants.NAME));
    }

    @Test
    @DisplayName("Can get an image")
    void testGetImage() throws Exception {

        // GIVEN
        given(service.getOne(AssetConstants.NUMBER)).willReturn(Assets.publicAccess());

        // WHEN + THEN
        mockMvc.perform(get("/images/{number}", AssetConstants.NUMBER))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.number").value(AssetConstants.NUMBER))
            .andExpect(jsonPath("$.content.name").value(AssetConstants.NAME))
            .andExpect(jsonPath("$.content.description").value(AssetConstants.DESCRIPTION));
    }

    @Test
    @DisplayName("Can update an image metadata")
    void testUpdateImageMetadata() throws Exception {
        // GIVEN
        given(service.updateMetadata(Assets.patch())).willReturn(Assets.patch());

        // WHEN + THEN
        mockMvc.perform(patch("/images/{number}", AssetConstants.NUMBER).contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "name": "%s",
                      "description": "%s",
                      "publicAccess": true
                    }
                    """.formatted(AssetConstants.NAME, AssetConstants.DESCRIPTION)))
            .andExpect(status().isOk());
    }

}
