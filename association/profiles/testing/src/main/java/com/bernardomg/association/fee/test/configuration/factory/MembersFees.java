
package com.bernardomg.association.fee.test.configuration.factory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.bernardomg.association.member.domain.model.MemberFees;
import com.bernardomg.association.member.domain.model.MemberFees.Fee;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.profile.domain.model.Name;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class MembersFees {

    public static final MemberFees activePaidCurrentMonth() {
        final MemberFees.Member          profile;
        final Collection<MemberFees.Fee> months;
        final Name                       name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        profile = new MemberFees.Member(MemberConstants.NUMBER, name, true);
        months = List.of(paid());
        return new MemberFees(profile, months);
    }

    public static final MemberFees inactivePaidCurrentMonth() {
        final MemberFees.Member          profile;
        final Collection<MemberFees.Fee> months;
        final Name                       name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME, Optional.empty());
        profile = new MemberFees.Member(MemberConstants.NUMBER, name, false);
        months = List.of(paid());
        return new MemberFees(profile, months);
    }

    private static final Fee paid() {
        return new Fee(FeeConstants.CURRENT_MONTH, true);
    }

}
