/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or Publishers.
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
 * Publishers OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.library.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.domain.model.Publisher;
import com.bernardomg.association.library.domain.repository.PublisherRepository;
import com.bernardomg.association.library.test.config.factory.PublisherConstants;
import com.bernardomg.association.library.test.config.factory.Publishers;
import com.bernardomg.association.library.usecase.service.DefaultPublisherService;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublisherService - create")
class TestPublisherServiceCreate {

    @Mock
    private PublisherRepository     publisherRepository;

    @InjectMocks
    private DefaultPublisherService service;

    public TestPublisherServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a publisher with an empty name, an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable execution;
        final Publisher        publisher;

        // GIVEN
        publisher = Publishers.emptyName();

        // WHEN
        execution = () -> service.create(publisher);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, FieldFailure.of("name", "empty", " "));
    }

    @Test
    @DisplayName("With a publisher with an existing name, an exception is thrown")
    void testCreate_Existing() {
        final ThrowingCallable execution;
        final Publisher        publisher;

        // GIVEN
        publisher = Publishers.valid();

        given(publisherRepository.exists(PublisherConstants.NAME)).willReturn(true);

        // WHEN
        execution = () -> service.create(publisher);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            FieldFailure.of("name", "existing", PublisherConstants.NAME));
    }

    @Test
    @DisplayName("With a valid publisher, the publisher is persisted")
    void testCreate_PersistedData() {
        final Publisher publisher;

        // GIVEN
        publisher = Publishers.valid();

        // WHEN
        service.create(publisher);

        // THEN
        verify(publisherRepository).save(Publishers.valid());
    }

    @Test
    @DisplayName("With a valid publisher, the created publisher is returned")
    void testCreate_ReturnedData() {
        final Publisher publisher;
        final Publisher created;

        // GIVEN
        publisher = Publishers.valid();

        given(publisherRepository.save(Publishers.valid())).willReturn(Publishers.valid());

        // WHEN
        created = service.create(publisher);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Publishers.valid());
    }

}
