
package com.bernardomg.association.membership.balance.service;

import com.bernardomg.association.membership.balance.model.MonthlyMemberBalance;
import com.bernardomg.association.membership.balance.model.request.ValidatedMemberBalanceQuery;

/**
 * Member service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface MemberBalanceService {

    public Iterable<? extends MonthlyMemberBalance> getBalance(final ValidatedMemberBalanceQuery query);

}
