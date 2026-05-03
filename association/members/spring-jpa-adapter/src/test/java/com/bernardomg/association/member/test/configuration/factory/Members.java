
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.Member.Name;

public final class Members {

    public static final Member alternative() {
        final Name name;

        name = new Name(MemberProfileConstants.ALTERNATIVE_FIRST_NAME, MemberProfileConstants.ALTERNATIVE_LAST_NAME);
        return new Member(MemberProfileConstants.ALTERNATIVE_NUMBER, name, true);
    }

    public static final Member forNumber(final long number) {
        final Name name;

        name = new Name("Name " + number, "Last name " + number);
        return new Member(number * 10, name, true);
    }

    public static final Member noRenew() {
        final Name name;

        name = new Name(MemberProfileConstants.FIRST_NAME, MemberProfileConstants.LAST_NAME);
        return new Member(MemberProfileConstants.NUMBER, name, false);
    }

    public static final Member valid() {
        final Name name;

        name = new Name(MemberProfileConstants.FIRST_NAME, MemberProfileConstants.LAST_NAME);
        return new Member(MemberProfileConstants.NUMBER, name, true);
    }

}
