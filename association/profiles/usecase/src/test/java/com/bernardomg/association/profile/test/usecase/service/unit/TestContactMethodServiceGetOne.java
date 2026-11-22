
package com.bernardomg.association.profile.test.usecase.service.unit;

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

import com.bernardomg.association.profile.domain.exception.MissingContactMethodException;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethods;
import com.bernardomg.association.profile.usecase.service.DefaultContactMethodService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultContactMethodService - getOne")
class TestContactMethodServiceGetOne {

    @Mock
    private ContactMethodRepository     ContactMethodRepository;

    @InjectMocks
    private DefaultContactMethodService service;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetOne() {
        final Optional<ContactMethod> contactMethodOptional;

        // GIVEN
        given(ContactMethodRepository.findOne(ContactMethodConstants.NUMBER))
            .willReturn(Optional.of(ContactMethods.email()));

        // WHEN
        contactMethodOptional = service.getOne(ContactMethodConstants.NUMBER);

        // THEN
        Assertions.assertThat(contactMethodOptional)
            .as("When there is data, then it is returned")
            .contains(ContactMethods.email());
    }

    @Test
    @DisplayName("When the contact method doesn't exist, then an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(ContactMethodRepository.findOne(ContactMethodConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(ContactMethodConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the contact method doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingContactMethodException.class);
    }

}
