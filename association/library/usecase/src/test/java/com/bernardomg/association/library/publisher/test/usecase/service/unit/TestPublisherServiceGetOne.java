
package com.bernardomg.association.library.publisher.test.usecase.service.unit;

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

import com.bernardomg.association.library.publisher.domain.exception.MissingPublisherException;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherConstants;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;
import com.bernardomg.association.library.publisher.usecase.service.DefaultPublisherService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultPublisherService - getOne")
class TestPublisherServiceGetOne {

    @Mock
    private PublisherRepository     publisherRepository;

    @InjectMocks
    private DefaultPublisherService service;

    @Test
    @DisplayName("When there is a publisher, then it is returned")
    void testGetOne() {
        final Optional<Publisher> author;

        // GIVEN
        given(publisherRepository.findOne(PublisherConstants.NUMBER)).willReturn(Optional.of(Publishers.valid()));

        // WHEN
        author = service.getOne(PublisherConstants.NUMBER);

        // THEN
        Assertions.assertThat(author)
            .as("When there is a publisher, then it is returned")
            .contains(Publishers.valid());
    }

    @Test
    @DisplayName("When there are no publishers, then an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(publisherRepository.findOne(PublisherConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(PublisherConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there are no publishers, then an exception is thrown")
            .isInstanceOf(MissingPublisherException.class);
    }

}
