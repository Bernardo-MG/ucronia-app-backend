
package com.bernardomg.association.member.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.profile.domain.model.Name;

public final class PublicMembers {

    public static final PublicMember alternative() {
        final Name name;

        name = new Name(MemberConstants.ALTERNATIVE_FIRST_NAME, MemberConstants.ALTERNATIVE_LAST_NAME);
        return new PublicMember(MemberConstants.ALTERNATIVE_NUMBER, name, true, Optional.empty());
    }

    public static final PublicMember forNumber(final long number) {
        final Name name;

        name = new Name("Name " + number, "Last name " + number);
        return new PublicMember(number * 10, name, true, Optional.empty());
    }

    public static final PublicMember noRenew() {
        final Name name;

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new PublicMember(MemberConstants.NUMBER, name, false, Optional.empty());
    }

    public static final PublicMember valid() {
        final Name name;

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new PublicMember(MemberConstants.NUMBER, name, true, Optional.empty());
    }

    private PublicMembers() {
        super();
    }

}
