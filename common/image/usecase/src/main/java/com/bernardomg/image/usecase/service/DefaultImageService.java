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

package com.bernardomg.image.usecase.service;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import com.bernardomg.image.domain.model.ImageContent;

import jakarta.transaction.Transactional;

/**
 * Default implementation of the activity service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Transactional
public final class DefaultImageService implements ImageService {

    private final RestClient          client;

    private final Map<String, String> images = Map.of("metroludik-2026.png",
        "https://drive.google.com/thumbnail?id=1Zv5g0i3PNEDwn30YPALy4HaqFDpZL_A3&sz=w1000");

    public DefaultImageService(final RestClient.Builder builder) {
        client = builder.defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0")
            .build();
    }

    @Override
    public final ImageContent getImage(final String name) {
        final ResponseEntity<byte[]> response;
        final String                 url;
        final MediaType              mediaType;

        url = images.get(name);

        if (url == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
        }

        response = client.get()
            .uri(URI.create(url))
            .retrieve()
            .toEntity(byte[].class);

        mediaType = response.getHeaders()
            .getContentType();

        return new ImageContent(response.getBody(),
            mediaType != null ? mediaType.toString() : MediaType.APPLICATION_OCTET_STREAM.toString());
    }
}
