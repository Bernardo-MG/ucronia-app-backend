
package com.bernardomg.association.membership.balance.service;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.bernardomg.association.membership.balance.model.MonthlyMemberBalance;
import com.bernardomg.association.membership.balance.model.request.ValidatedMemberBalanceQuery;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
@Slf4j
public final class DefaultMemberBalanceService implements MemberBalanceService {

    @Override
    public final Iterable<? extends MonthlyMemberBalance> getBalance(final ValidatedMemberBalanceQuery query) {
        // TODO Auto-generated method stub
        return Collections.emptyList();
    }

}
