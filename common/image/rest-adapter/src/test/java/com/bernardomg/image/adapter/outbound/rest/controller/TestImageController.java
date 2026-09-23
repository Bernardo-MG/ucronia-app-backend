/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.outbound.rest.controller;

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

import com.bernardomg.content.domain.model.Content;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.test.configuration.factory.Contents;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.image.usecase.service.ImageService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageController")
class TestImageController {

    private MockMvc      mockMvc;

    @Mock
    private ImageService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new ImageController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("Can create an image")
    void testCreateImage() throws Exception {
        final MockMultipartFile file;

        // GIVEN
        file = new MockMultipartFile("file", ImageConstants.NAME, MediaType.IMAGE_PNG_VALUE, ImageConstants.DATA);
        given(service.create(any(Image.class), any(Content.class))).willReturn(Images.valid());

        // WHEN + THEN
        mockMvc.perform(multipart("/images").file(file)
            .param("name", ImageConstants.NAME)
            .param("description", ImageConstants.DESCRIPTION))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Can get all the images")
    void testGetAllImages() throws Exception {
        final Page<Image> page;

        // GIVEN
        page = new Page<>(List.of(Images.valid()), 10, 1, 1, 1, 1, true, true, Sorting.unsorted());
        given(service.getAll(any(Pagination.class), any(Sorting.class))).willReturn(page);

        // WHEN + THEN
        mockMvc.perform(get("/images").param("page", "1")
            .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].number").value(ImageConstants.NUMBER))
            .andExpect(jsonPath("$.content[0].name").value(ImageConstants.NAME));
    }

    @Test
    @DisplayName("Can get an image content")
    void testGetContent() throws Exception {
        // GIVEN
        given(service.getContent(ImageConstants.NUMBER)).willReturn(Contents.image());

        // WHEN + THEN
        mockMvc.perform(get("/images/{number}/content", ImageConstants.NUMBER))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Can get an image")
    void testGetImage() throws Exception {

        // GIVEN
        given(service.getOne(ImageConstants.NUMBER)).willReturn(Images.valid());

        // WHEN + THEN
        mockMvc.perform(get("/images/{number}", ImageConstants.NUMBER))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.number").value(ImageConstants.NUMBER))
            .andExpect(jsonPath("$.content.name").value(ImageConstants.NAME))
            .andExpect(jsonPath("$.content.description").value(ImageConstants.DESCRIPTION));
    }

    @Test
    @DisplayName("Can update an image metadata")
    void testUpdateImageMetadata() throws Exception {
        // GIVEN
        given(service.updateMetadata(Images.patch())).willReturn(Images.patch());

        // WHEN + THEN
        mockMvc.perform(patch("/images/{number}", ImageConstants.NUMBER).contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "name": "%s",
                      "description": "%s"
                    }
                    """.formatted(ImageConstants.NAME, ImageConstants.DESCRIPTION)))
            .andExpect(status().isOk());
    }

}
