
package com.bernardomg.asset.adapter.outbound.rest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.asset.adapter.outbound.rest.security.AssetReadAuthorizer;
import com.bernardomg.asset.test.configuration.factory.AssetConstants;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.usecase.service.AssetService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageController - updateImageMetadata")
class TestImageControllerUpdateImageMetadata {

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
    @DisplayName("When an image metadata is updated, then the change is accepted")
    void testUpdateImageMetadata() throws Exception {
        // GIVEN
        given(service.updateMetadata(Assets.patchImage())).willReturn(Assets.patchImage());

        // WHEN
        mockMvc.perform(patch("/assets/images/{number}", AssetConstants.NUMBER).contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "name": "%s",
                      "description": "%s",
                      "publicAccess": true
                    }
                    """.formatted(AssetConstants.NAME, AssetConstants.DESCRIPTION)))
            // THEN
            .andExpect(status().isOk());
    }

}
