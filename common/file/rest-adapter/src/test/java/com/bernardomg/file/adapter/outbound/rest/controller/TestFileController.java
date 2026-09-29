
package com.bernardomg.file.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.asset.adapter.outbound.rest.security.SpringSecurityAssetReadAuthorizer;
import com.bernardomg.file.test.configuration.factory.Contents;
import com.bernardomg.file.test.configuration.factory.FileConstants;
import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.file.usecase.service.FileService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;

@ExtendWith(MockitoExtension.class)
@DisplayName("FileController")
class TestFileController {

    private MockMvc                     mockMvc;

    @Mock
    private ResourcePermissionEvaluator permissionEvaluator;

    @Mock
    private FileService                 service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator;

        validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
            .standaloneSetup(
                new FileController(service, new SpringSecurityAssetReadAuthorizer(permissionEvaluator, "FILE")))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("Can create a file")
    void testCreateFile() throws Exception {
        final MockMultipartFile file;

        // GIVEN
        file = new MockMultipartFile("file", FileConstants.NAME, MediaType.APPLICATION_PDF_VALUE, FileConstants.DATA);
        given(service.create(any(Asset.class), any(Content.class))).willReturn(Files.publicAccess());

        // WHEN + THEN
        mockMvc.perform(multipart("/files").file(file)
            .param("name", FileConstants.NAME)
            .param("description", FileConstants.DESCRIPTION)
            .param("publicAccess", "true"))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Can get all the files")
    void testGetAllFiles() throws Exception {
        final Page<Asset> page;

        // GIVEN
        page = new Page<>(List.of(Files.publicAccess()), 10, 1, 1, 1, 1, true, true, Sorting.unsorted());
        given(service.getAllPublic(any(Pagination.class), any(Sorting.class))).willReturn(page);

        // WHEN + THEN
        mockMvc.perform(get("/files").param("page", "1")
            .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].number").value(FileConstants.NUMBER))
            .andExpect(jsonPath("$.content[0].name").value(FileConstants.NAME));
    }

    @Test
    @DisplayName("Can get file content")
    void testGetContent() throws Exception {
        // GIVEN
        given(service.getOne(FileConstants.NUMBER)).willReturn(Files.publicAccess());
        given(service.getContent(FileConstants.NUMBER)).willReturn(Contents.file());

        // WHEN + THEN
        mockMvc.perform(get("/files/{number}/content", FileConstants.NUMBER))
            .andExpect(status().isOk())
            .andExpect(header().exists(HttpHeaders.CONTENT_DISPOSITION))
            .andExpect(result -> {
                final String         header;
                final ContentDisposition disposition;

                header = result.getResponse()
                    .getHeader(HttpHeaders.CONTENT_DISPOSITION);
                disposition = ContentDisposition.parse(header);

                Assertions.assertThat(disposition.getType())
                    .isEqualTo("attachment");
                Assertions.assertThat(disposition.getFilename())
                    .isEqualTo("file.pdf");
            })
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, FileConstants.PDF_MEDIA_TYPE))
            .andExpect(header().longValue(HttpHeaders.CONTENT_LENGTH, FileConstants.DATA.length))
            .andExpect(header().string("X-Content-Type-Options", "nosniff"));
    }

    @Test
    @DisplayName("Can get a file")
    void testGetFile() throws Exception {

        // GIVEN
        given(service.getOne(FileConstants.NUMBER)).willReturn(Files.publicAccess());

        // WHEN + THEN
        mockMvc.perform(get("/files/{number}", FileConstants.NUMBER))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.number").value(FileConstants.NUMBER))
            .andExpect(jsonPath("$.content.name").value(FileConstants.NAME))
            .andExpect(jsonPath("$.content.description").value(FileConstants.DESCRIPTION));
    }

    @Test
    @DisplayName("Can update file metadata")
    void testUpdateFileMetadata() throws Exception {
        // GIVEN
        given(service.updateMetadata(Files.patch())).willReturn(Files.patch());

        // WHEN + THEN
        mockMvc.perform(patch("/files/{number}", FileConstants.NUMBER).contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "name": "%s",
                      "description": "%s",
                      "publicAccess": true
                    }
                    """.formatted(FileConstants.NAME, FileConstants.DESCRIPTION)))
            .andExpect(status().isOk());
    }

}
