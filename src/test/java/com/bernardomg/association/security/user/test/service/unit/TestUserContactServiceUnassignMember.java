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

package com.bernardomg.association.security.user.test.service.unit;

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

import com.bernardomg.association.person.domain.repository.ContactRepository;
import com.bernardomg.association.security.user.domain.repository.UserContactRepository;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.association.security.user.usecase.service.DefaultUserContactService;
import com.bernardomg.security.user.domain.exception.MissingUsernameException;
import com.bernardomg.security.user.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserContactService - unassign member")
class TestUserContactServiceUnassignMember {

    @Mock
    private ContactRepository         contactsRepository;

    @InjectMocks
    private DefaultUserContactService service;

    @Mock
    private UserContactRepository     userContactRepository;

    @Mock
    private UserRepository            userRepository;

    @Test
    @DisplayName("With a member assigned to the user, it removes the member")
    void testUnassignContact() {

        // GIVEN
        given(userRepository.exists(UserConstants.USERNAME)).willReturn(true);

        // WHEN
        service.unassignContact(UserConstants.USERNAME);

        // THEN
        verify(userContactRepository).unassignContact(UserConstants.USERNAME);
    }

    @Test
    @DisplayName("With no member, it throws an exception")
    void testUnassignContact_NoMember() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.exists(UserConstants.USERNAME)).willReturn(false);

        // WHEN
        execution = () -> service.unassignContact(UserConstants.USERNAME);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingUsernameException.class);
    }

}
