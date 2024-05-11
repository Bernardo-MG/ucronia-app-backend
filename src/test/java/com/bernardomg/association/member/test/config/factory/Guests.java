
package com.bernardomg.association.member.test.config.factory;

import com.bernardomg.association.member.domain.model.Guest;

public final class Guests {

    public static final Guest emptyName() {
        return Guest.builder()
            .withNumber(GuestConstants.NUMBER)
            .withName(" ")
            .build();
    }

    public static final Guest nameChange() {
        return Guest.builder()
            .withNumber(GuestConstants.NUMBER)
            .withName("Donor 123")
            .build();
    }

    public static final Guest paddedWithWhitespaces() {
        return Guest.builder()
            .withNumber(GuestConstants.NUMBER)
            .withName(" " + GuestConstants.NAME + " ")
            .build();
    }

    public static final Guest valid() {
        return Guest.builder()
            .withNumber(GuestConstants.NUMBER)
            .withName(GuestConstants.NAME)
            .build();
    }

}
