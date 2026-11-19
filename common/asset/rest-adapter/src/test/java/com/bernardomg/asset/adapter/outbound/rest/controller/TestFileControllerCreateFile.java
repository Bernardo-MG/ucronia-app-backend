
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
@DisplayName("FileController - createFile")
class TestFileControllerCreateFile {

    @InjectMocks
    private FileController controller;

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
    @DisplayName("When a file is submitted, then it can be created")
    void testCreateFile() throws Exception {
        final MockMultipartFile file;

        // GIVEN
        file = new MockMultipartFile("file", AssetConstants.NAME, MediaType.APPLICATION_PDF_VALUE, AssetConstants.DATA);
        given(service.create(any(Asset.class), any(Content.class))).willReturn(Assets.publicAccess());

        // WHEN
        mockMvc.perform(multipart("/assets/files").file(file)
            .param("name", AssetConstants.NAME)
            .param("description", AssetConstants.DESCRIPTION)
            .param("publicAccess", "true"))
            // THEN
            .andExpect(status().isCreated());
    }

}
