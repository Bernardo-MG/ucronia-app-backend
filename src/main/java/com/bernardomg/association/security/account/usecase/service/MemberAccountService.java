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

package com.bernardomg.association.security.account.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.model.Contact;
import com.bernardomg.association.security.account.domain.model.ContactAccount;
import com.bernardomg.association.security.user.domain.repository.UserContactRepository;
import com.bernardomg.security.account.domain.model.Account;
import com.bernardomg.security.account.usecase.service.AccountService;

/**
 * Default account service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Transactional
public final class MemberAccountService implements AccountService {

    /**
     * Logger for the class.
     */
    private static final Logger         log = LoggerFactory.getLogger(MemberAccountService.class);

    private final UserContactRepository userPersonRepository;

    private final AccountService        wrapped;

    public MemberAccountService(final AccountService wrppd, final UserContactRepository userMemberRepo) {
        super();

        wrapped = Objects.requireNonNull(wrppd);
        userPersonRepository = Objects.requireNonNull(userMemberRepo);
    }

    @Override
    public final Optional<Account> getCurrentUser() {
        final Optional<Account> wrappedAccount;
        final Optional<Account> result;
        final Account           account;
        final Optional<Contact> person;

        log.debug("Getting account for user in session");

        wrappedAccount = wrapped.getCurrentUser();
        if (wrappedAccount.isPresent()) {
            person = userPersonRepository.findByUsername(wrappedAccount.get()
                .getUsername());

            account = new ContactAccount(wrappedAccount.get()
                .getEmail(),
                wrappedAccount.get()
                    .getUsername(),
                wrappedAccount.get()
                    .getName(),
                person.orElse(null));
            result = Optional.of(account);
        } else {
            log.debug("Missing authentication object");
            result = wrappedAccount;
        }

        return result;
    }

    @Override
    public final Account update(final Account account) {

        log.debug("Updating account {} using data {}", account.getUsername(), account);

        return wrapped.update(account);
    }

}
