
package com.bernardomg.association.fee.test.configuration.factory;

import java.util.Collection;
import java.util.List;

import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.model.MemberFees.Fee;
import com.bernardomg.association.person.domain.model.ContactName;
import com.bernardomg.association.person.test.configuration.factory.ContactConstants;

public final class MembersFees {

    public static final MemberFees activePaidCurrentMonth() {
        final MemberFees.Member          contact;
        final Collection<MemberFees.Fee> months;
        final ContactName                name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contact = new MemberFees.Member(ContactConstants.NUMBER, name, true);
        months = List.of(paid());
        return new MemberFees(contact, months);
    }

    public static final MemberFees inactivePaidCurrentMonth() {
        final MemberFees.Member          contact;
        final Collection<MemberFees.Fee> months;
        final ContactName                name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contact = new MemberFees.Member(ContactConstants.NUMBER, name, false);
        months = List.of(paid());
        return new MemberFees(contact, months);
    }

    private static final Fee paid() {
        return new Fee(FeeConstants.CURRENT_MONTH, true);
    }

}
