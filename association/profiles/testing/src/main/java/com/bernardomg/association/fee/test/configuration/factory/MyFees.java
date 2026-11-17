
package com.bernardomg.association.fee.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.model.MyFee;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.profile.domain.model.Name;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class MyFees {

    public static final MyFee paid() {
        final Fee.Transaction transaction;
        final Name            name;
        final FeeMember       member;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        transaction = new Fee.Transaction(TransactionConstants.INDEX, FeeConstants.PAYMENT_DATE);
        member = new FeeMember(MemberConstants.NUMBER, name);
        return new MyFee(FeeConstants.DATE, true, member, Optional.of(transaction));
    }

    private MyFees() {
        super();
    }

}
