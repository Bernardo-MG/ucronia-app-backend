
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.ucronia.openapi.model.ContactDto;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.FeeCalendarDto;
import com.bernardomg.ucronia.openapi.model.MembershipDto;

public final class FeeCalendarDtoMapper {

    public static final FeeCalendarDto toDto(final FeeCalendar feeCalendar) {
        final ContactDto     contact;
        final ContactNameDto contactName;
        final MembershipDto  membership;

        contactName = new ContactNameDto().firstName(feeCalendar.member()
            .name()
            .firstName())
            .lastName(feeCalendar.member()
                .name()
                .lastName())
            .fullName(feeCalendar.member()
                .name()
                .fullName());
        membership = new MembershipDto().active(feeCalendar.member()
            .membership()
            .active());
        contact = new ContactDto().name(contactName)
            .membership(membership)
            .number(feeCalendar.member()
                .number());
        return new FeeCalendarDto().year(feeCalendar.year())
            .member(contact);
    }

    private FeeCalendarDtoMapper() {
        super();
    }

}
