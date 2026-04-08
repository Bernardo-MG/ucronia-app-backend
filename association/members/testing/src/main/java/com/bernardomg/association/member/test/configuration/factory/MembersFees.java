
package com.bernardomg.association.member.test.configuration.factory;

import java.util.Collection;
import java.util.List;

import com.bernardomg.association.fee.domain.model.FeeMember.MemberName;
import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.model.MemberFees.Fee;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class MembersFees {

    public static final MemberFees activePaidCurrentMonth() {
        final MemberFees.Member          profile;
        final Collection<MemberFees.Fee> months;
        final MemberName                 name;

        name = new MemberName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new MemberFees.Member(ProfileConstants.NUMBER, name, true);
        months = List.of(paid());
        return new MemberFees(profile, months);
    }

    public static final MemberFees inactivePaidCurrentMonth() {
        final MemberFees.Member          profile;
        final Collection<MemberFees.Fee> months;
        final MemberName                 name;

        name = new MemberName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new MemberFees.Member(ProfileConstants.NUMBER, name, false);
        months = List.of(paid());
        return new MemberFees(profile, months);
    }

    private static final Fee paid() {
        return new Fee(FeeConstants.CURRENT_MONTH, true);
    }

}
