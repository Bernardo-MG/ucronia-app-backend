
package com.bernardomg.association.security.account.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.security.account.domain.PersonAccount;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.security.account.domain.model.Account;
import com.bernardomg.security.account.domain.repository.AccountRepository;
import com.bernardomg.security.account.usecase.service.AccountService;
import com.bernardomg.security.account.usecase.service.DefaultAccountService;

/**
 * Default account service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Transactional
public final class MemberAccountService implements AccountService {

    private final UserPersonRepository userMemberRepository;

    private final AccountService       wrapped;

    public MemberAccountService(final AccountRepository accountRepo, final UserPersonRepository userMemberRepo) {
        super();

        wrapped = new DefaultAccountService(accountRepo);
        userMemberRepository = Objects.requireNonNull(userMemberRepo);
    }

    @Override
    public final Optional<Account> getCurrentUser() {
        final Optional<Account> basicAccount;
        final Optional<Account> result;
        final Account           account;
        final Optional<Person>  member;

        basicAccount = wrapped.getCurrentUser();
        if (basicAccount.isPresent()) {
            member = userMemberRepository.findByUsername(basicAccount.get()
                .getUsername());

            account = PersonAccount.builder()
                .withUsername(basicAccount.get()
                    .getUsername())
                .withName(basicAccount.get()
                    .getName())
                .withEmail(basicAccount.get()
                    .getEmail())
                .withPerson(member.orElse(null))
                .build();
            result = Optional.of(account);
        } else {
            result = basicAccount;
        }

        return result;
    }

    @Override
    public final Account update(final Account account) {
        return wrapped.update(account);
    }

}
