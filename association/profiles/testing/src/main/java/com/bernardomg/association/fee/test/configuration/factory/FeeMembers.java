
package com.bernardomg.association.fee.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.profile.domain.model.Name;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class FeeMembers {

    public static final FeeMember valid() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new FeeMember(MemberConstants.NUMBER, name);
    }

}
