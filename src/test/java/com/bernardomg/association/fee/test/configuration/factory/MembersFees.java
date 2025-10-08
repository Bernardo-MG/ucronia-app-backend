
package com.bernardomg.association.fee.test.configuration.factory;

import java.util.Collection;
import java.util.List;

import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.model.MemberFees.Fee;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;

public final class MembersFees {

    public static final MemberFees activePaidCurrentMonth() {
        final MemberFees.Member          person;
        final Collection<MemberFees.Fee> months;
        final PersonName                 name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new MemberFees.Member(PersonConstants.NUMBER, name, true);
        months = List.of(paid());
        return new MemberFees(person, months);
    }

    public static final MemberFees inactivePaidCurrentMonth() {
        final MemberFees.Member          person;
        final Collection<MemberFees.Fee> months;
        final PersonName                 name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new MemberFees.Member(PersonConstants.NUMBER, name, false);
        months = List.of(paid());
        return new MemberFees(person, months);
    }

    private static final Fee paid() {
        return new Fee(FeeConstants.CURRENT_MONTH, true);
    }

}
