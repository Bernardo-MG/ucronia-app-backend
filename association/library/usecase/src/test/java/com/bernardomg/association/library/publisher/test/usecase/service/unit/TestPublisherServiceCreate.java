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

package com.bernardomg.association.library.publisher.test.usecase.service.unit;

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

import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherConstants;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;
import com.bernardomg.association.library.publisher.usecase.service.DefaultPublisherService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

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

        given(publisherRepository.findNextNumber()).willReturn(PublisherConstants.NUMBER);

        // WHEN
        execution = () -> service.create(publisher);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, new FieldFailure("empty", "name", ""));
    }

    @Test
    @DisplayName("With a publisher with an existing name, an exception is thrown")
    void testCreate_Existing() {
        final ThrowingCallable execution;
        final Publisher        publisher;

        // GIVEN
        publisher = Publishers.toCreate();

        given(publisherRepository.findNextNumber()).willReturn(PublisherConstants.NUMBER);

        given(publisherRepository.existsByName(PublisherConstants.NAME)).willReturn(true);

        // WHEN
        execution = () -> service.create(publisher);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "name", PublisherConstants.NAME));
    }

    @Test
    @DisplayName("With a publisher with padded name, the publisher is persisted")
    void testCreate_Padded_PersistedData() {
        final Publisher publisher;

        // GIVEN
        publisher = Publishers.padded();

        given(publisherRepository.findNextNumber()).willReturn(PublisherConstants.NUMBER);

        // WHEN
        service.create(publisher);

        // THEN
        verify(publisherRepository).save(Publishers.valid());
    }

    @Test
    @DisplayName("With a valid publisher, the publisher is persisted")
    void testCreate_PersistedData() {
        final Publisher publisher;

        // GIVEN
        publisher = Publishers.toCreate();

        given(publisherRepository.findNextNumber()).willReturn(PublisherConstants.NUMBER);

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
        publisher = Publishers.toCreate();

        given(publisherRepository.findNextNumber()).willReturn(PublisherConstants.NUMBER);

        given(publisherRepository.save(Publishers.valid())).willReturn(Publishers.valid());

        // WHEN
        created = service.create(publisher);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Publishers.valid());
    }

}
