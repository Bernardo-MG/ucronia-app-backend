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

import java.io.IOException;
import java.util.Objects;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.bernardomg.image.domain.model.ImageContent;
import com.bernardomg.image.usecase.service.ImageService;

/**
 * Activity REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class ImageController implements ImageApi {

    private final ImageService service;

    public ImageController(final ImageService service) {
        super();

        this.service = Objects.requireNonNull(service);
    }

    @Override
    public ResponseEntity<Resource> getImage(final String name) {
        final ImageContent content;

        content = service.getImage(name);

        // TODO: return the image content structure
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(content.mediaType()))
            .body(new ByteArrayResource(content.data()));
    }

    @Override
    public ResponseEntity<Void> uploadImage(final String name, final MultipartFile file) {
        final String mediaType;
        final byte[] data;

        if (file.getContentType() == null) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        } else {
            mediaType = file.getContentType();
        }

        try {
            data = file.getBytes();
        } catch (final IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to read image", ex);
        }

        service.uploadImage(name, new ImageContent(data, mediaType));
        return ResponseEntity.noContent()
            .build();
    }

}
