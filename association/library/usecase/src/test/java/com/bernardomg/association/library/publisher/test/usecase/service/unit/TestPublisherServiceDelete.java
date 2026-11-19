
package com.bernardomg.association.library.publisher.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherConstants;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;
import com.bernardomg.association.library.publisher.usecase.service.DefaultPublisherService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultPublisherService - delete")
class TestPublisherServiceDelete {

    @Mock
    private PublisherRepository     publisherRepository;

    @InjectMocks
    private DefaultPublisherService service;

    @Test
    @DisplayName("When deleting a publisher, then the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(publisherRepository.findOne(PublisherConstants.NUMBER)).willReturn(Optional.of(Publishers.valid()));

        // WHEN
        service.delete(PublisherConstants.NUMBER);

        // THEN
        verify(publisherRepository).delete(PublisherConstants.NUMBER);
    }

    @Test
    @DisplayName("When the publisher doesn't exist, then an exception is thrown")
    void testDelete_NotExisting_Exception() {
        final ThrowingCallable action;

        // GIVEN
        given(publisherRepository.findOne(PublisherConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(PublisherConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the publisher doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingPublisherException.class);
    }

}
