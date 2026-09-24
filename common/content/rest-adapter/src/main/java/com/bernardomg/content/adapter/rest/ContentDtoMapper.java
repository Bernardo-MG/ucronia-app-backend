
package com.bernardomg.content.adapter.rest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.bernardomg.content.domain.model.Content;

public final class ContentDtoMapper {

    public static ResponseEntity<Resource> toAttachment(final Content content, final String filename) {
        final ContentDisposition disposition;

        Objects.requireNonNull(filename, "Filename can't be null");

        disposition = ContentDisposition.attachment()
            .filename(filename, StandardCharsets.UTF_8)
            .build();
        return toResponse(content, disposition);
    }

    public static Content toContent(final MultipartFile file) {
        final String mediaType;

        if (file.getContentType() == null) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        } else {
            mediaType = file.getContentType();
        }

        try {
            return new Content(file.getInputStream(), file.getSize(), mediaType);
        } catch (final IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to read content", ex);
        }
    }

    public static ResponseEntity<Resource> toInline(final Content content) {
        return toResponse(content, ContentDisposition.inline()
            .build());
    }

    private static ResponseEntity<Resource> toResponse(final Content content, final ContentDisposition disposition) {
        Objects.requireNonNull(content, "Content can't be null");

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(content.mediaType()))
            .contentLength(content.size())
            .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
            .header("X-Content-Type-Options", "nosniff")
            .body(new InputStreamResource(content.data()));
    }

    private ContentDtoMapper() {
        super();
    }

}
