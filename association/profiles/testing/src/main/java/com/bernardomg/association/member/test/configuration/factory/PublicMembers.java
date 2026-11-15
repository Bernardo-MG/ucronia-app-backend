
package com.bernardomg.association.member.test.configuration.factory;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Optional;

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.profile.domain.model.Name;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class PublicMembers {

    public static final PublicMember alternative() {
        final Name name;

        name = new Name(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME,
            Optional.empty());
        return new PublicMember(MemberConstants.ALTERNATIVE_NUMBER, MemberConstants.ALTERNATIVE_NUMBER, name, true,
            true, Optional.empty(), 0L, Optional.empty());
    }

    public static final PublicMember forNumber(final long number) {
        final Name    name;
        final Instant date;

        name = new Name("Name " + number, "Last name " + number, Optional.empty());
        date = YearMonth.of(FeeConstants.YEAR_VALUE, (int) number + 1)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        return new PublicMember(number * 10, number * 10, name, true, true, Optional.ofNullable(date), 1,
            Optional.ofNullable(date));
    }

    public static final PublicMember forNumberNotPaid(final long number) {
        final Name name;

        name = new Name("Name " + number, "Last name " + number, Optional.empty());
        return new PublicMember(number * 10, number * 10, name, true, true, Optional.empty(), 0, Optional.empty());
    }

    public static final PublicMember inactive() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new PublicMember(MemberConstants.NUMBER, MemberConstants.MEMBER_NUMBER, name, false, false,
            Optional.empty(), 0L, Optional.empty());
    }

    public static final PublicMember noRenew() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new PublicMember(MemberConstants.NUMBER, MemberConstants.MEMBER_NUMBER, name, true, false,
            Optional.empty(), 0L, Optional.empty());
    }

    public static final PublicMember valid() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new PublicMember(MemberConstants.NUMBER, MemberConstants.MEMBER_NUMBER, name, true, true,
            Optional.empty(), 0L, Optional.empty());
    }

    public static final PublicMember withMembership(final Instant joined, final long totalMonths) {
        final Name    name;
        final Instant lastDate;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        lastDate = joined.atOffset(ZoneOffset.UTC)
            .plusMonths(totalMonths - 1)
            .toInstant();
        return new PublicMember(MemberConstants.NUMBER, MemberConstants.MEMBER_NUMBER, name, true, true,
            Optional.ofNullable(joined), totalMonths, Optional.ofNullable(lastDate));
    }

    public static final PublicMember withMembership(final Instant joined, final long totalMonths,
            final Instant lastDate) {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        return new PublicMember(MemberConstants.NUMBER, MemberConstants.MEMBER_NUMBER, name, true, true,
            Optional.ofNullable(joined), totalMonths, Optional.ofNullable(lastDate));
    }

    public static final PublicMember withMembership(final long number, final Instant joined, final long totalMonths) {
        final Name name;

        name = new Name("Name " + number, "Last name " + number, Optional.empty());
        return new PublicMember(number * 10, number * 10, name, true, true, Optional.ofNullable(joined), totalMonths,
            Optional.ofNullable(joined));
    }

    private PublicMembers() {
        super();
    }

}
