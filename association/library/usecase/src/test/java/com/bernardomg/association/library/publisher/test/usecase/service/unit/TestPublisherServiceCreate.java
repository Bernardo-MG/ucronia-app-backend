
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
@DisplayName("DefaultPublisherService - create")
class TestPublisherServiceCreate {

    @Mock
    private PublisherRepository     publisherRepository;

    @InjectMocks
    private DefaultPublisherService service;

    @Test
    @DisplayName("When there is a publisher with an empty name, then an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable action;
        final Publisher        publisher;

        // GIVEN
        publisher = Publishers.emptyName();

        // WHEN
        action = () -> service.create(publisher);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("empty", "name", ""));
    }

    @Test
    @DisplayName("When there is a publisher with an existing name, then an exception is thrown")
    void testCreate_Existing() {
        final ThrowingCallable action;
        final Publisher        publisher;

        // GIVEN
        publisher = Publishers.toCreate();

        given(publisherRepository.existsByName(PublisherConstants.NAME)).willReturn(true);

        // WHEN
        action = () -> service.create(publisher);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "name", PublisherConstants.NAME));
    }

    @Test
    @DisplayName("When there is a publisher with padded name, then the publisher is persisted")
    void testCreate_Padded_PersistedData() {
        final Publisher publisher;

        // GIVEN
        publisher = Publishers.padded();

        // WHEN
        service.create(publisher);

        // THEN
        verify(publisherRepository).save(Publishers.toCreate());
    }

    @Test
    @DisplayName("When there is a valid publisher, then the publisher is persisted")
    void testCreate_PersistedData() {
        final Publisher publisher;

        // GIVEN
        publisher = Publishers.toCreate();

        // WHEN
        service.create(publisher);

        // THEN
        verify(publisherRepository).save(Publishers.toCreate());
    }

    @Test
    @DisplayName("When there is a valid publisher, then the created publisher is returned")
    void testCreate_ReturnedData() {
        final Publisher publisher;
        final Publisher created;

        // GIVEN
        publisher = Publishers.toCreate();

        given(publisherRepository.save(Publishers.toCreate())).willReturn(Publishers.valid());

        // WHEN
        created = service.create(publisher);

        // THEN
        Assertions.assertThat(created)
            .as("When there is a valid publisher, then the created publisher is returned")
            .isEqualTo(Publishers.valid());
    }

}
