
package com.bernardomg.association.fee.test.configuration.factory;

import java.util.Collection;
import java.util.List;

import com.bernardomg.association.fee.domain.model.FeeMember.MemberName;
import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.model.MemberFees.Fee;

public final class MembersFees {

    public static final MemberFees activePaidCurrentMonth() {
        final MemberFees.Member          profile;
        final Collection<MemberFees.Fee> months;
        final MemberName                 name;

        name = new MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        profile = new MemberFees.Member(MemberConstants.NUMBER, name, true);
        months = List.of(paid());
        return new MemberFees(profile, months);
    }

    public static final MemberFees inactivePaidCurrentMonth() {
        final MemberFees.Member          profile;
        final Collection<MemberFees.Fee> months;
        final MemberName                 name;

        name = new MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        profile = new MemberFees.Member(MemberConstants.NUMBER, name, false);
        months = List.of(paid());
        return new MemberFees(profile, months);
    }

    private static final Fee paid() {
        return new Fee(FeeConstants.CURRENT_MONTH, true);
    }

}
