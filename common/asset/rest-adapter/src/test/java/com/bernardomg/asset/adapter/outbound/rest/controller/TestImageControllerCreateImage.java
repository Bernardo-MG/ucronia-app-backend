
package com.bernardomg.asset.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.asset.adapter.outbound.rest.security.AssetReadAuthorizer;
import com.bernardomg.asset.test.configuration.factory.AssetConstants;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.usecase.service.AssetService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageController - createImage")
class TestImageControllerCreateImage {

    @InjectMocks
    private ImageController controller;

    private MockMvc                     mockMvc;

    @Mock
    private AssetReadAuthorizer authorizer;

    @Mock
    private AssetService                service;

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
    @DisplayName("When an image is submitted, then it can be created")
    void testCreateImage() throws Exception {
        final MockMultipartFile file;

        // GIVEN
        file = new MockMultipartFile("file", AssetConstants.NAME, MediaType.IMAGE_PNG_VALUE, AssetConstants.DATA);
        given(service.create(any(Asset.class), any(Content.class))).willReturn(Assets.publicAccess());

        // WHEN
        mockMvc.perform(multipart("/assets/images").file(file)
            .param("name", AssetConstants.NAME)
            .param("description", AssetConstants.DESCRIPTION)
            .param("public", "true"))
            // THEN
            .andExpect(status().isCreated());
    }

}
