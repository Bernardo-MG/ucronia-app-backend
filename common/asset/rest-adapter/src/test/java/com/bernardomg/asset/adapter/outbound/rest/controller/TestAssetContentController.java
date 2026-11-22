
package com.bernardomg.asset.adapter.outbound.rest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bernardomg.asset.adapter.outbound.rest.security.AssetReadAuthorizer;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.test.configuration.factory.AssetConstants;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.test.configuration.factory.Contents;
import com.bernardomg.asset.usecase.service.AssetContentService;

@ExtendWith(MockitoExtension.class)
@DisplayName("AssetContentController - getAssetContent")
class TestAssetContentController {

    @Mock
    private AssetReadAuthorizer authorizer;

    @InjectMocks
    private AssetContentController controller;

    private MockMvc             mockMvc;

    @Mock
    private AssetContentService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .build();
    }

    @Test
    @DisplayName("When content is requested for download, then it is returned as an attachment")
    void testGetContent_Attachment() throws Exception {
        final Asset asset;

        // GIVEN
        asset = Assets.publicAccess();
        given(service.getOne(AssetConstants.NUMBER)).willReturn(asset);
        given(service.getContent(asset)).willReturn(Contents.pdf());

        // WHEN
        mockMvc.perform(get("/assets/{number}/content", AssetConstants.NUMBER).param("download", "true"))
            // THEN
            .andExpect(status().isOk())
            .andExpect(result -> {
                final ContentDisposition disposition;

                disposition = ContentDisposition.parse(result.getResponse()
                    .getHeader(HttpHeaders.CONTENT_DISPOSITION));
                Assertions.assertThat(disposition.getType())
                    .as("Returns content as an attachment when requested")
                    .isEqualTo("attachment");
                Assertions.assertThat(disposition.getFilename())
                    .as("Returns content as an attachment when requested")
                    .isEqualTo(asset.name());
            });
    }

    @Test
    @DisplayName("When content is requested without a download flag, then it is returned inline")
    void testGetContent_Inline() throws Exception {
        final Asset asset;

        // GIVEN
        asset = Assets.publicAccess();
        given(service.getOne(AssetConstants.NUMBER)).willReturn(asset);
        given(service.getContent(asset)).willReturn(Contents.pdf());

        // WHEN
        mockMvc.perform(get("/assets/{number}/content", AssetConstants.NUMBER))
            // THEN
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "inline"))
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, AssetConstants.PDF_MEDIA_TYPE))
            .andExpect(header().longValue(HttpHeaders.CONTENT_LENGTH, AssetConstants.DATA.length));
    }

    @Test
    @DisplayName("When private content is requested, then caching is disabled")
    void testGetContent_Private() throws Exception {
        final Asset asset;

        // GIVEN
        asset = Assets.privateAccess();
        given(service.getOne(AssetConstants.NUMBER)).willReturn(asset);
        given(service.getContent(asset)).willReturn(Contents.pdf());

        // WHEN
        mockMvc.perform(get("/assets/{number}/content", AssetConstants.NUMBER))
            // THEN
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.CACHE_CONTROL, "private, no-store"));
    }

}
