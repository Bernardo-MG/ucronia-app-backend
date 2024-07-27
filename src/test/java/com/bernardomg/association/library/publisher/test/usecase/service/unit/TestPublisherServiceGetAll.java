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

package com.bernardomg.association.library.publisher.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.config.factory.Publishers;
import com.bernardomg.association.library.publisher.usecase.service.DefaultPublisherService;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublisherService - get all")
class TestPublisherServiceGetAll {

    @Mock
    private PublisherRepository     publisherRepository;

    @InjectMocks
    private DefaultPublisherService service;

    public TestPublisherServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("When there are publishers, they are returned")
    void testGetAll() {
        final Pageable            pageable;
        final Iterable<Publisher> publishers;

        // GIVEN
        pageable = Pageable.unpaged();

        given(publisherRepository.findAll(pageable)).willReturn(List.of(Publishers.valid()));

        // WHEN
        publishers = service.getAll(pageable);

        // THEN
        Assertions.assertThat(publishers)
            .as("authors")
            .containsExactly(Publishers.valid());
    }

    @Test
    @DisplayName("When there are no publishers, nothing is returned")
    void testGetAll_NoData() {
        final Pageable            pageable;
        final Iterable<Publisher> publishers;

        // GIVEN
        pageable = Pageable.unpaged();

        given(publisherRepository.findAll(pageable)).willReturn(List.of());

        // WHEN
        publishers = service.getAll(pageable);

        // THEN
        Assertions.assertThat(publishers)
            .as("authors")
            .isEmpty();
    }

}
