
package com.bernardomg.association.security.account.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.security.account.domain.model.PersonAccount;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
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
    private static final Logger        log = LoggerFactory.getLogger(MemberAccountService.class);

    private final UserPersonRepository userPersonRepository;

    private final AccountService       wrapped;

    public MemberAccountService(final AccountService wrppd, final UserPersonRepository userMemberRepo) {
        super();

        wrapped = Objects.requireNonNull(wrppd);
        userPersonRepository = Objects.requireNonNull(userMemberRepo);
    }

    @Override
    public final Optional<Account> getCurrentUser() {
        final Optional<Account> wrappedAccount;
        final Optional<Account> result;
        final Account           account;
        final Optional<Person>  person;

        log.debug("Getting account for user in session");

        wrappedAccount = wrapped.getCurrentUser();
        if (wrappedAccount.isPresent()) {
            person = userPersonRepository.findByUsername(wrappedAccount.get()
                .getUsername());

            account = PersonAccount.builder()
                .withUsername(wrappedAccount.get()
                    .getUsername())
                .withName(wrappedAccount.get()
                    .getName())
                .withEmail(wrappedAccount.get()
                    .getEmail())
                .withPerson(person.orElse(null))
                .build();
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
