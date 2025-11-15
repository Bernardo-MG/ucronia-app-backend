/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.member.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.contact.domain.exception.MissingContactMethodException;
import com.bernardomg.association.contact.domain.repository.ContactMethodRepository;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.member.domain.model.MemberContact;
import com.bernardomg.association.member.domain.repository.MemberContactRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberContacts;
import com.bernardomg.association.member.usecase.service.DefaultMemberContactService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberContactService - create")
class TestMemberContactServiceCreate {

    @Mock
    private ContactMethodRepository     contactMethodRepository;

    @Mock
    private MemberContactRepository     memberContactRepository;

    @InjectMocks
    private DefaultMemberContactService service;

    public TestMemberContactServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a contact with an existing identifier, an exception is thrown")
    void testCreate_IdentifierExists() {
        final ThrowingCallable execution;
        final MemberContact    member;

        // GIVEN
        member = MemberContacts.toCreate();

        given(memberContactRepository.existsByIdentifier(ContactConstants.IDENTIFIER)).willReturn(true);

        // WHEN
        execution = () -> service.create(member);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "identifier", ContactConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("With a contact with an existing identifier, but the identifier is empty, no exception is thrown")
    void testCreate_IdentifierExistsAndEmpty() {
        final MemberContact member;

        // GIVEN
        member = MemberContacts.toCreateNoIdentifier();

        given(memberContactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        service.create(member);

        // THEN
        verify(memberContactRepository).save(MemberContacts.noIdentifier());
        verify(memberContactRepository, Mockito.never()).existsByIdentifier(ContactConstants.IDENTIFIER);
    }

    @Test
    @DisplayName("With a contact having padding whitespaces in first and last name, these whitespaces are removed and the contact is persisted")
    void testCreate_Padded_PersistedData() {
        final MemberContact member;

        // GIVEN
        member = MemberContacts.padded();

        given(memberContactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        service.create(member);

        // THEN
        verify(memberContactRepository).save(MemberContacts.active());
    }

    @Test
    @DisplayName("With a valid contact, the contact is persisted")
    void testCreate_PersistedData() {
        final MemberContact member;

        // GIVEN
        member = MemberContacts.toCreate();

        given(memberContactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        service.create(member);

        // THEN
        verify(memberContactRepository).save(MemberContacts.active());
    }

    @Test
    @DisplayName("With a valid contact, the created contact is returned")
    void testCreate_ReturnedData() {
        final MemberContact member;
        final MemberContact created;

        // GIVEN
        member = MemberContacts.toCreate();

        given(memberContactRepository.save(MemberContacts.active())).willReturn(MemberContacts.active());
        given(memberContactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        created = service.create(member);

        // THEN
        Assertions.assertThat(created)
            .as("contact")
            .isEqualTo(MemberContacts.active());
    }

    @Test
    @DisplayName("With a contact with a not existing contact method, an exception is thrown")
    void testCreate_WithContactChannel_NotExistingContactMethod() {
        final MemberContact    member;
        final ThrowingCallable execution;

        // GIVEN
        member = MemberContacts.withEmail();

        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.create(member);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingContactMethodException.class);
    }

    @Test
    @DisplayName("With a contact with a contact method, the contact is persisted")
    void testCreate_WithContactChannel_PersistedData() {
        final MemberContact member;

        // GIVEN
        member = MemberContacts.withEmail();

        given(memberContactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);
        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(member);

        // THEN
        verify(memberContactRepository).save(MemberContacts.withEmail());
    }

}
