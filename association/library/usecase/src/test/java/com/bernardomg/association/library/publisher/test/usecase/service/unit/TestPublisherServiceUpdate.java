
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

import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystemConstants;
import com.bernardomg.association.library.publisher.domain.exception.MissingPublisherException;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherConstants;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;
import com.bernardomg.association.library.publisher.usecase.service.DefaultPublisherService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultPublisherService - update")
class TestPublisherServiceUpdate {

    @Mock
    private PublisherRepository     publisherRepository;

    @InjectMocks
    private DefaultPublisherService service;

    @Test
    @DisplayName("When there is a publisher with an empty name, then an exception is thrown")
    void testUpdate_EmptyName() {
        final ThrowingCallable action;
        final Publisher        publisher;

        // GIVEN
        publisher = Publishers.emptyName();

        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);

        // WHEN
        action = () -> service.update(publisher);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("empty", "name", ""));
    }

    @Test
    @DisplayName("When there is a publisher with a name existing for another user, then an exception is thrown")
    void testUpdate_ExistsForAnother() {
        final Publisher        publisher;
        final ThrowingCallable action;

        // GIVEN
        publisher = Publishers.valid();

        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(publisherRepository.existsByNameForAnother(PublisherConstants.NAME, GameSystemConstants.NUMBER))
            .willReturn(true);

        // WHEN
        action = () -> service.update(publisher);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "name", PublisherConstants.NAME));
    }

    @Test
    @DisplayName("When there is a not existing publisher, then an exception is thrown")
    void testUpdate_NotExisting() {
        final Publisher        publisher;
        final ThrowingCallable action;

        // GIVEN
        publisher = Publishers.valid();

        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(publisher);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing publisher, then an exception is thrown")
            .isInstanceOf(MissingPublisherException.class);
    }

    @Test
    @DisplayName("When there is a valid publisher, then the publisher is persisted")
    void testUpdate_PersistedData() {
        final Publisher publisher;

        // GIVEN
        publisher = Publishers.valid();

        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(publisher);

        // THEN
        verify(publisherRepository).save(Publishers.valid());
    }

    @Test
    @DisplayName("When there is a valid publisher, then the created publisher is returned")
    void testUpdate_ReturnedData() {
        final Publisher publisher;
        final Publisher created;

        // GIVEN
        publisher = Publishers.valid();

        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);

        given(publisherRepository.save(Publishers.valid())).willReturn(Publishers.valid());

        // WHEN
        created = service.update(publisher);

        // THEN
        Assertions.assertThat(created)
            .as("When there is a valid publisher, then the created publisher is returned")
            .isEqualTo(Publishers.valid());
    }

}
