
package com.bernardomg.asset.adapter.outbound.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bernardomg.asset.domain.exception.ContentEmptyException;
import com.bernardomg.asset.domain.exception.ContentTooLargeException;
import com.bernardomg.asset.domain.exception.ContentTypeNotAllowedException;

@RestControllerAdvice
public class ContentExceptionHandler {

    @ExceptionHandler(ContentTooLargeException.class)
    public ResponseEntity<Void> handleContentTooLarge() {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
            .build();
    }

    @ExceptionHandler(ContentTypeNotAllowedException.class)
    public ResponseEntity<Void> handleContentTypeNotAllowed() {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .build();
    }

    @ExceptionHandler(ContentEmptyException.class)
    public ResponseEntity<Void> handleEmptyContent() {
        return ResponseEntity.badRequest()
            .build();
    }

}
