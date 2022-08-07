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

package com.bernardomg.association.pagination.argument;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.association.pagination.model.Direction;
import com.bernardomg.association.pagination.model.Sort;

import lombok.extern.slf4j.Slf4j;

/**
 * Argument resolver for sorting data.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class SortArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * Default constructor.
     */
    public SortArgumentResolver() {
        super();
    }

    @Override
    public final Sort resolveArgument(final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory) throws Exception {
        final String sortedText;
        final String property;
        final String directionText;
        final Boolean sorted;
        final Direction direction;
        final Sort sort;

        sortedText = webRequest.getParameter("sorted");
        sorted = parseBoolean(sortedText);

        if (sorted) {
            property = webRequest.getParameter("property");
            directionText = webRequest.getParameter("direction");

            if (property == null) {
                // No sort parameters
                log.trace("No sorting data received, using disabled sort");
                sort = Sort.disabled();
            } else {
                if ("desc".equalsIgnoreCase(directionText)) {
                    direction = Direction.DESC;
                } else {
                    direction = Direction.ASC;
                }

                log.trace("Sorting by property {} and direction {}", property,
                    direction);
                sort = Sort.of(property, direction);
            }
        } else {
            // No sort
            log.trace("Trace disabled for request");
            sort = Sort.disabled();
        }

        return sort;
    }

    @Override
    public final boolean supportsParameter(final MethodParameter parameter) {
        return Sort.class.equals(parameter.getParameterType());
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

}
