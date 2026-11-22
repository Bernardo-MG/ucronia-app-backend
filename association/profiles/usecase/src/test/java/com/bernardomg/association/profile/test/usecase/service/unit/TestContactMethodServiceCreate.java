
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

import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethods;
import com.bernardomg.association.profile.usecase.service.DefaultContactMethodService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultContactMethodService - create")
class TestContactMethodServiceCreate {

    @Mock
    private ContactMethodRepository     ContactMethodRepository;

    @InjectMocks
    private DefaultContactMethodService service;

    @Test
    @DisplayName("When there is a profile method with an existing name, then an exception is thrown")
    void testCreate_ExistingName() {
        final ThrowingCallable action;
        final ContactMethod    contactMethod;

        // GIVEN
        contactMethod = ContactMethods.email();

        given(ContactMethodRepository.existsByName(ContactMethodConstants.EMAIL)).willReturn(true);

        // WHEN
        action = () -> service.create(contactMethod);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "name", ContactMethodConstants.EMAIL));
    }

    @Test
    @DisplayName("When there is a valid contact method, then the contact method is persisted")
    void testCreate_PersistedData() {
        final ContactMethod contactMethod;

        // GIVEN
        contactMethod = ContactMethods.email();

        // WHEN
        service.create(contactMethod);

        // THEN
        verify(ContactMethodRepository).save(ContactMethods.email());
    }

    @Test
    @DisplayName("When there is a valid contact method, then the created contact method is returned")
    void testCreate_ReturnedData() {
        final ContactMethod contactMethod;
        final ContactMethod created;

        // GIVEN
        contactMethod = ContactMethods.email();

        given(ContactMethodRepository.save(ContactMethods.email())).willReturn(ContactMethods.email());

        // WHEN
        created = service.create(contactMethod);

        // THEN
        Assertions.assertThat(created)
            .as("contact method")
            .isEqualTo(ContactMethods.email());
    }

}
