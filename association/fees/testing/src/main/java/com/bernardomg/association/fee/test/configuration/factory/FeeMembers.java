
package com.bernardomg.association.fee.test.configuration.factory;

import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.model.FeeMember.MemberName;

public final class FeeMembers {

    public static final FeeMember valid() {
        final MemberName name;

        name = new MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new FeeMember(MemberConstants.NUMBER, name);
    }

}
