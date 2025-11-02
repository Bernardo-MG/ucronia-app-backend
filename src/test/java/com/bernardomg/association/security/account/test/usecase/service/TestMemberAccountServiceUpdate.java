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
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.security.account.test.configuration.factory.BasicAccounts;
import com.bernardomg.association.security.account.usecase.service.MemberAccountService;
import com.bernardomg.association.security.user.domain.repository.UserContactRepository;
import com.bernardomg.security.account.domain.model.Account;
import com.bernardomg.security.account.domain.model.BasicAccount;
import com.bernardomg.security.account.usecase.service.AccountService;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberAccountService - update")
class TestMemberAccountServiceUpdate {

    @InjectMocks
    private MemberAccountService  service;

    @Mock
    private UserContactRepository userContactRepository;

    @Mock
    private AccountService        wrapped;

    @Test
    @DisplayName("When updating an account, the updated one is returned")
    void testUpdate_Returned() {
        final BasicAccount account;
        final Account      updated;

        // GIVEN
        account = BasicAccounts.valid();
        given(wrapped.update(account)).willReturn(account);

        // WHEN
        updated = service.update(account);

        // THEN
        Assertions.assertThat(updated)
            .as("updated account")
            .isEqualTo(account);
    }

    @Test
    @DisplayName("When updating an account, it is sent to the wrapped service")
    void testUpdate_SendToWrapped() {
        final BasicAccount account;

        // GIVEN
        account = BasicAccounts.valid();

        // WHEN
        service.update(account);

        // THEN
        verify(wrapped).update(account);
    }

}
