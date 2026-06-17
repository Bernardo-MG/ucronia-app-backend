
package com.bernardomg.association.fee.test.configuration.factory;

import com.bernardomg.association.member.domain.model.FeeMember;
import com.bernardomg.association.member.domain.model.Name;

public final class FeeMembers {

    public static final FeeMember valid() {
        final Name name;

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new FeeMember(MemberConstants.NUMBER, name);
    }

}
