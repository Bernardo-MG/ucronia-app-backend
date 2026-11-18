
package com.bernardomg.association.contacts.domain.model;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public record ContactInfo(String map, String calendar, String email, String instagram, String telegram) {

    public ContactInfo(final String map, final String calendar, final String email, final String instagram,
            final String telegram) {
        Objects.requireNonNull(map, "Map can't be null");
        Objects.requireNonNull(calendar, "Calendar can't be null");
        Objects.requireNonNull(email, "Email can't be null");
        Objects.requireNonNull(instagram, "Instagram can't be null");
        Objects.requireNonNull(telegram, "Telegram can't be null");

        this.map = StringUtils.trim(map);
        this.calendar = StringUtils.trim(calendar);
        this.email = StringUtils.trim(email);
        this.instagram = StringUtils.trim(instagram);
        this.telegram = StringUtils.trim(telegram);
    }

}
