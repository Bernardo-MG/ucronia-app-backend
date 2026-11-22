
package com.bernardomg.association.profile.test.usecase.service.unit;

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

import com.bernardomg.association.profile.domain.exception.MissingContactMethodException;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethods;
import com.bernardomg.association.profile.usecase.service.DefaultContactMethodService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultContactMethodService - update")
class TestContactMethodServiceUpdate {

    @Mock
    private ContactMethodRepository     ContactMethodRepository;

    @InjectMocks
    private DefaultContactMethodService service;

    @Test
    @DisplayName("When there is a profile method with an existing name, then an exception is thrown")
    void testUpdate_ExistingName() {
        final ThrowingCallable action;
        final ContactMethod    contactMethod;

        // GIVEN
        contactMethod = ContactMethods.email();

        given(ContactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(true);
        given(
            ContactMethodRepository.existsByNameForAnother(ContactMethodConstants.NUMBER, ContactMethodConstants.EMAIL))
                .willReturn(true);

        // WHEN
        action = () -> service.update(contactMethod);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "name", ContactMethodConstants.EMAIL));
    }

    @Test
    @DisplayName("When there is a not existing contact method, then an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final ContactMethod    contactMethod;
        final ThrowingCallable action;

        // GIVEN
        contactMethod = ContactMethods.email();

        given(ContactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(contactMethod);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing contact method, then an exception is thrown")
            .isInstanceOf(MissingContactMethodException.class);
    }

    @Test
    @DisplayName("When updating a contact method, then the change is persisted")
    void testUpdate_PersistedData() {
        final ContactMethod contactMethod;

        // GIVEN
        contactMethod = ContactMethods.email();

        given(ContactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(contactMethod);

        // THEN
        verify(ContactMethodRepository).save(ContactMethods.email());
    }

    @Test
    @DisplayName("When updating an active contact method, then the change is returned")
    void testUpdate_ReturnedData() {
        final ContactMethod contactMethod;
        final ContactMethod updated;

        // GIVEN
        contactMethod = ContactMethods.email();

        given(ContactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(true);
        given(ContactMethodRepository.save(ContactMethods.email())).willReturn(ContactMethods.email());

        // WHEN
        updated = service.update(contactMethod);

        // THEN
        Assertions.assertThat(updated)
            .as("contact method")
            .isEqualTo(ContactMethods.email());
    }

}
