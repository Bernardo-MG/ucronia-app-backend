/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.image.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bernardomg.image.domain.model.ImageContent;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.usecase.service.ImageService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageController")
class TestImageController {

    private MockMvc      mockMvc;

    @Mock
    private ImageService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ImageController(service))
            .build();
    }

    @Test
    @DisplayName("When deleting an image, it is accepted and deleted")
    void testDeleteImage() throws Exception {
        // WHEN + THEN
        mockMvc.perform(delete("/images/{name}", ImageConstants.NAME))
            .andExpect(status().isNoContent());

        verify(service).delete(ImageConstants.NAME);
    }

    @Test
    @DisplayName("When updating an image, it is accepted and persisted")
    void testUpdateImage() throws Exception {
        final ArgumentCaptor<ImageContent> contentCaptor;
        final MockMultipartFile            file;

        // GIVEN
        contentCaptor = ArgumentCaptor.forClass(ImageContent.class);
        file = new MockMultipartFile("file", ImageConstants.NAME, MediaType.IMAGE_PNG_VALUE, ImageConstants.DATA);

        // WHEN + THEN
        mockMvc.perform(multipart(HttpMethod.PUT, "/images/{name}", ImageConstants.NAME).file(file))
            .andExpect(status().isNoContent());

        verify(service).update(eq(ImageConstants.NAME), contentCaptor.capture());

        Assertions.assertThat(contentCaptor.getValue()
            .data())
            .containsExactly(ImageConstants.DATA);
        Assertions.assertThat(contentCaptor.getValue()
            .mediaType())
            .isEqualTo(MediaType.IMAGE_PNG_VALUE);
    }

    @Test
    @DisplayName("When uploading an image, it is accepted and persisted")
    void testUploadImage() throws Exception {
        final ArgumentCaptor<ImageContent> contentCaptor;
        final MockMultipartFile            file;

        // GIVEN
        contentCaptor = ArgumentCaptor.forClass(ImageContent.class);
        file = new MockMultipartFile("file", ImageConstants.NAME, MediaType.IMAGE_PNG_VALUE, ImageConstants.DATA);

        // WHEN + THEN
        mockMvc.perform(multipart(HttpMethod.POST, "/images/{name}", ImageConstants.NAME).file(file))
            .andExpect(status().isCreated());

        verify(service).create(eq(ImageConstants.NAME), contentCaptor.capture());

        Assertions.assertThat(contentCaptor.getValue()
            .data())
            .containsExactly(ImageConstants.DATA);
        Assertions.assertThat(contentCaptor.getValue()
            .mediaType())
            .isEqualTo(MediaType.IMAGE_PNG_VALUE);
    }

}
