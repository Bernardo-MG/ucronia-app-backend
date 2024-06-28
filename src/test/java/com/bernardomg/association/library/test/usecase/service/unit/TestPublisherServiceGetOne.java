/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.library.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.domain.model.Publisher;
import com.bernardomg.association.library.domain.repository.PublisherRepository;
import com.bernardomg.association.library.test.config.factory.PublisherConstants;
import com.bernardomg.association.library.test.config.factory.Publishers;
import com.bernardomg.association.library.usecase.service.DefaultPublisherService;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublisherService - get one")
class TestPublisherServiceGetOne {

    @Mock
    private PublisherRepository     publisherRepository;

    @InjectMocks
    private DefaultPublisherService service;

    public TestPublisherServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("When there is a publisher, it is returned")
    void testGetOne() {
        final Optional<Publisher> author;

        // GIVEN
        given(publisherRepository.findOne(PublisherConstants.NAME)).willReturn(Optional.of(Publishers.valid()));

        // WHEN
        author = service.getOne(PublisherConstants.NAME);

        // THEN
        Assertions.assertThat(author)
            .contains(Publishers.valid());
    }

    @Test
    @DisplayName("When there are no publishers, an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(publisherRepository.findOne(PublisherConstants.NAME)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOne(PublisherConstants.NAME);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingAuthorException.class);
    }

}
