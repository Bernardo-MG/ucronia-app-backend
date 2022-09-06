/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.mvc.error.handler;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.mvc.response.model.DefaultResponse;
import com.bernardomg.mvc.response.model.Response;
import com.bernardomg.validation.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

/**
 * Captures and handles exceptions for all the controllers.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Default constructor.
     */
    public GlobalExceptionHandler() {
        super();
    }

    @ExceptionHandler({ RuntimeException.class })
    public final ResponseEntity<Object> handleExceptionDefault(final Exception ex, final WebRequest request)
            throws Exception {
        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ ValidationException.class })
    public final ResponseEntity<Object> handleValidationException(final ValidationException ex,
            final WebRequest request) throws Exception {
        final Iterable<String>           messages;
        final Response<Iterable<String>> response;

        log.warn(ex.getMessage(), ex);

        messages = ex.getFailures()
            .stream()
            .map(Failure::getMessage)
            .collect(Collectors.toList());

        response = new DefaultResponse<>(messages);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Transforms Spring's field error into our custom field error.
     *
     * @param error
     *            error object to transform
     * @return our custom error object
     */
    private final FieldFailure toFieldError(final org.springframework.validation.FieldError error) {
        log.error("{}.{} with value {}: {}", error.getObjectName(), error.getField(), error.getRejectedValue(),
            error.getDefaultMessage());

        return FieldFailure.of(error.getDefaultMessage(), error.getObjectName(), error.getField(),
            error.getRejectedValue());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception ex, final Object body,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final Response<String> response;
        final String           message;

        log.error(ex.getMessage());

        if (ex.getMessage() == null) {
            message = "";
        } else {
            message = ex.getMessage();
        }

        response = new DefaultResponse<>(message);

        return super.handleExceptionInternal(ex, response, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final Iterable<FieldFailure>           errors;
        final Response<Iterable<FieldFailure>> response;

        // TODO: Test this transformation
        errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(this::toFieldError)
            .collect(Collectors.toList());

        response = new DefaultResponse<>(errors);

        return super.handleExceptionInternal(ex, response, headers, status, request);
    }

}
