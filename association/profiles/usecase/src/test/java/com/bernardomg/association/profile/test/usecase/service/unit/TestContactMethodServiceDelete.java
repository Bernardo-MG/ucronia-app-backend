
package com.bernardomg.association.profile.test.usecase.service.unit;

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

import com.bernardomg.association.profile.domain.exception.MissingContactMethodException;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethods;
import com.bernardomg.association.profile.usecase.service.DefaultContactMethodService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultContactMethodService - delete")
class TestContactMethodServiceDelete {

    @Mock
    private ContactMethodRepository     ContactMethodRepository;

    @InjectMocks
    private DefaultContactMethodService service;

    @Test
    @DisplayName("When deleting, then the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(ContactMethodRepository.findOne(ContactMethodConstants.NUMBER))
            .willReturn(Optional.of(ContactMethods.email()));

        // WHEN
        service.delete(ContactMethodConstants.NUMBER);

        // THEN
        verify(ContactMethodRepository).delete(ContactMethodConstants.NUMBER);
    }

    @Test
    @DisplayName("When the contact method doesn't exist, then an exception is thrown")
    void testDelete_NotExisting_NotRemovesEntity() {
        final ThrowingCallable action;

        // GIVEN
        given(ContactMethodRepository.findOne(ContactMethodConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(ContactMethodConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the contact method doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingContactMethodException.class);
    }

}
