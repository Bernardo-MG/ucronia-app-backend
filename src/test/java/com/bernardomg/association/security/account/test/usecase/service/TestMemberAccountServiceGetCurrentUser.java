/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.security.account.test.usecase.service;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.person.test.config.factory.Persons;
import com.bernardomg.association.security.account.test.config.factory.BasicAccounts;
import com.bernardomg.association.security.account.test.config.factory.PersonAccounts;
import com.bernardomg.association.security.account.usecase.service.MemberAccountService;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.association.security.user.test.config.factory.UserConstants;
import com.bernardomg.security.account.domain.model.Account;
import com.bernardomg.security.account.usecase.service.AccountService;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberAccountService - get current user")
class TestMemberAccountServiceGetCurrentUser {

    @InjectMocks
    private MemberAccountService service;

    @Mock
    private UserPersonRepository userPersonRepository;

    @Mock
    private AccountService       wrapped;

    @Test
    @DisplayName("When there is a current user, it is returned")
    void testGetCurrentUser() {
        final Optional<Account> account;

        // GIVEN
        given(wrapped.getCurrentUser()).willReturn(Optional.of(BasicAccounts.valid()));
        given(userPersonRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.of(Persons.valid()));

        // WHEN
        account = service.getCurrentUser();

        // THEN
        Assertions.assertThat(account)
            .as("account")
            .contains(PersonAccounts.valid());
    }

    @Test
    @DisplayName("When there is a current user, but no assigned person, it is returned")
    void testGetCurrentUser_NoPerson() {
        final Optional<Account> account;

        // GIVEN
        given(wrapped.getCurrentUser()).willReturn(Optional.of(BasicAccounts.valid()));
        given(userPersonRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.empty());

        // WHEN
        account = service.getCurrentUser();

        // THEN
        Assertions.assertThat(account)
            .as("account")
            .contains(PersonAccounts.noPerson());
    }

    @Test
    @DisplayName("When there is no current user, nothing is returned")
    void testGetCurrentUser_NoUser() {
        final Optional<Account> account;

        // GIVEN
        given(wrapped.getCurrentUser()).willReturn(Optional.empty());

        // WHEN
        account = service.getCurrentUser();

        // THEN
        Assertions.assertThat(account)
            .as("account")
            .isEmpty();
    }

}
