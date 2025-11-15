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
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.MemberContact;
import com.bernardomg.association.member.domain.repository.MemberContactRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberContacts;
import com.bernardomg.association.member.usecase.service.DefaultMemberContactService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberContactService - update")
class TestMemberContactServiceUpdate {

    @Mock
    private MemberContactRepository     memberContactRepository;

    @Mock
    private ContactMethodRepository     memberMethodRepository;

    @InjectMocks
    private DefaultMemberContactService service;

    public TestMemberContactServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With a member with an existing identifier, an exception is thrown")
    void testCreate_IdentifierExists() {
        final ThrowingCallable execution;
        final MemberContact    member;

        // GIVEN
        member = MemberContacts.nameChange();

        given(memberContactRepository.exists(ContactConstants.NUMBER)).willReturn(true);
        given(
            memberContactRepository.existsByIdentifierForAnother(ContactConstants.NUMBER, ContactConstants.IDENTIFIER))
                .willReturn(true);

        // WHEN
        execution = () -> service.update(member);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "identifier", ContactConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("With a member with an existing identifier, but the identifier is empty, no exception is thrown")
    void testCreate_IdentifierExistsAndEmpty() {
        final MemberContact member;

        // GIVEN
        member = MemberContacts.noIdentifier();

        given(memberContactRepository.exists(ContactConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(member);

        // THEN
        verify(memberContactRepository).save(MemberContacts.noIdentifier());
        verify(memberContactRepository, Mockito.never()).existsByIdentifierForAnother(ContactConstants.NUMBER,
            ContactConstants.IDENTIFIER);
    }

    @Test
    @DisplayName("With a not existing member, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final MemberContact    member;
        final ThrowingCallable execution;

        // GIVEN
        member = MemberContacts.nameChange();

        given(memberContactRepository.exists(ContactConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(member);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberException.class);
    }

    @Test
    @DisplayName("With a member having padding whitespaces in first and last name, these whitespaces are removed")
    void testUpdate_Padded_PersistedData() {
        final MemberContact member;

        // GIVEN
        member = MemberContacts.padded();

        given(memberContactRepository.exists(ContactConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(member);

        // THEN
        verify(memberContactRepository).save(MemberContacts.active());
    }

    @Test
    @DisplayName("When updating a member, the change is persisted")
    void testUpdate_PersistedData() {
        final MemberContact member;

        // GIVEN
        member = MemberContacts.nameChange();

        given(memberContactRepository.exists(ContactConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(member);

        // THEN
        verify(memberContactRepository).save(MemberContacts.nameChange());
    }

    @Test
    @DisplayName("When updating an active member, the change is returned")
    void testUpdate_ReturnedData() {
        final MemberContact member;
        final MemberContact updated;

        // GIVEN
        member = MemberContacts.nameChange();

        given(memberContactRepository.exists(ContactConstants.NUMBER)).willReturn(true);
        given(memberContactRepository.save(MemberContacts.nameChange())).willReturn(MemberContacts.nameChange());

        // WHEN
        updated = service.update(member);

        // THEN
        Assertions.assertThat(updated)
            .as("member")
            .isEqualTo(MemberContacts.nameChange());
    }

    @Test
    @DisplayName("With a member with a not existing member method, an exception is thrown")
    void testUpdate_WithMember_NotExistingContactMethod() {
        final MemberContact    member;
        final ThrowingCallable execution;

        // GIVEN
        member = MemberContacts.withEmail();

        given(memberContactRepository.exists(ContactConstants.NUMBER)).willReturn(true);
        given(memberMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(member);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingContactMethodException.class);
    }

    @Test
    @DisplayName("When updating a member with a member method, the change is persisted")
    void testUpdate_WithMember_PersistedData() {
        final MemberContact member;

        // GIVEN
        member = MemberContacts.withEmail();

        given(memberContactRepository.exists(ContactConstants.NUMBER)).willReturn(true);
        given(memberMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(member);

        // THEN
        verify(memberContactRepository).save(MemberContacts.withEmail());
    }

}
