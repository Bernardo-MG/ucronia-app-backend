
package com.bernardomg.association.fee.model;

import java.util.Calendar;

import lombok.Data;

@Data
public final class DtoMemberFee implements MemberFee {

    private Calendar date;

    private Long     id;

    private Long     memberId;

    private String   name;

    private Boolean  paid;

    private String   surname;

    public DtoMemberFee() {
        super();
    }

    public DtoMemberFee(final Long id, final Long memberId, final String name, final String surname,
            final Calendar date, final Boolean paid) {
        super();

        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.paid = paid;
    }

}
