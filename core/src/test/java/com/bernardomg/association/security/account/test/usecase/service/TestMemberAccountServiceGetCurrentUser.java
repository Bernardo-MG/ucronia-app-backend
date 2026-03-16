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

import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.association.security.account.test.configuration.factory.BasicAccounts;
import com.bernardomg.association.security.account.test.configuration.factory.ProfileAccounts;
import com.bernardomg.association.security.account.usecase.service.MemberAccountService;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.security.account.domain.model.Account;
import com.bernardomg.security.account.usecase.service.AccountService;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberAccountService - get current user")
class TestMemberAccountServiceGetCurrentUser {

    @InjectMocks
    private MemberAccountService  service;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private AccountService        wrapped;

    @Test
    @DisplayName("When there is a current user, it is returned")
    void testGetCurrentUser() {
        final Optional<Account> account;

        // GIVEN
        given(wrapped.getCurrentUser()).willReturn(Optional.of(BasicAccounts.valid()));
        given(userProfileRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.of(Profiles.valid()));

        // WHEN
        account = service.getCurrentUser();

        // THEN
        Assertions.assertThat(account)
            .as("account")
            .contains(ProfileAccounts.valid());
    }

    @Test
    @DisplayName("When there is a current user, but no assigned profile, it is returned")
    void testGetCurrentUser_NoProfile() {
        final Optional<Account> account;

        // GIVEN
        given(wrapped.getCurrentUser()).willReturn(Optional.of(BasicAccounts.valid()));
        given(userProfileRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.empty());

        // WHEN
        account = service.getCurrentUser();

        // THEN
        Assertions.assertThat(account)
            .as("account")
            .contains(ProfileAccounts.noProfile());
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
