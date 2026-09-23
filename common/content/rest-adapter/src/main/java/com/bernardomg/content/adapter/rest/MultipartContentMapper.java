package com.bernardomg.content.adapter.rest;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.bernardomg.content.domain.model.Content;

public final class MultipartContentMapper {

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

    private MultipartContentMapper() {
        super();
    }

}
