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

package com.bernardomg.association.security.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.bernardomg.association.security.account.usecase.service.MemberAccountService;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.security.account.domain.repository.AccountRepository;
import com.bernardomg.security.account.usecase.service.AccountService;
import com.bernardomg.security.account.usecase.service.DefaultAccountService;

@Configuration
public class AssociationAccountConfig {

    public AssociationAccountConfig() {
        super();
    }

    @Primary
    @Bean("memberAccountService")
    public MemberAccountService getMemberAccountService(final AccountRepository accountRepository,
            final UserPersonRepository userPersonRepository) {
        final AccountService wrapped;

        wrapped = new DefaultAccountService(accountRepository);
        return new MemberAccountService(wrapped, userPersonRepository);
    }

}
