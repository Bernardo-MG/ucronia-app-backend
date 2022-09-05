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

package com.bernardomg.mvc.pagination.argument;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.mvc.pagination.model.Pagination;

import lombok.extern.slf4j.Slf4j;

/**
 * Argument resolver for pagination data.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class PaginationArgumentResolver implements HandlerMethodArgumentResolver {
    
    private static final Pagination DEFAULT_PAGINATION = Pagination.first();

    /**
     * Default constructor.
     */
    public PaginationArgumentResolver() {
        super();
    }

    @Override
    public final Pagination resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final String     pagedText;
        final String     pageText;
        final String     sizeText;
        final Boolean    paged;
        final Integer    page;
        final Integer    size;
        final Pagination pagination;

        pagedText = webRequest.getParameter("paged");
        paged = parseBoolean(pagedText);

        if (paged) {
            pageText = webRequest.getParameter("page");
            sizeText = webRequest.getParameter("size");

            if ((pageText == null) && (sizeText == null)) {
                // No pagination parameters
                log.trace("No pagination data received, using default pagination");
                pagination = DEFAULT_PAGINATION;
            } else {
                page = parseInteger(pageText);
                size = parseSize(sizeText);

                log.trace("Building page {} with size {}", page, size);
                // Checks size. If it is invalid then the default size is used
                if (size > 0) {
                    pagination = Pagination.of(page, size);
                } else {
                    log.trace("Invalid size {}, using default size", size);
                    pagination = Pagination.of(page);
                }
            }
        } else {
            // No pagination
            // TODO: Not all endpoints may allow disabling pagination
            log.trace("Pagination disabled for request");
            pagination = Pagination.disabled();
        }

        return pagination;
    }

    @Override
    public final boolean supportsParameter(final MethodParameter parameter) {
        return Pagination.class.equals(parameter.getParameterType());
    }

    /**
     * Transforms the text into its boolean value.
     *
     * @param text
     *            text with the boolean flag
     * @return paged as boolean
     */
    private final Boolean parseBoolean(final String text) {
        final Boolean result;

        if (text == null) {
            result = true;
        } else {
            result = Boolean.valueOf(text);
        }

        return result;
    }

    /**
     * Transforms the text into its numeric value.
     *
     * @param text
     *            text with the integer page
     * @return page as integer
     */
    private final Integer parseInteger(final String text) {
        final Integer page;

        if (text == null) {
            page = 0;
        } else {
            page = Integer.valueOf(text);
        }

        return page;
    }

    /**
     * Transforms the size text into its numeric value.
     *
     * @param sizeText
     *            text with the pagination size
     * @return size as integer
     */
    private final Integer parseSize(final String sizeText) {
        final Integer size;

        if (sizeText == null) {
            // No size
            size = -1;
            log.trace("No size received");
        } else {
            size = Integer.valueOf(sizeText);
        }

        return size;
    }

}
