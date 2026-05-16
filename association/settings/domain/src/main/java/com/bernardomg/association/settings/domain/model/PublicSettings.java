
package com.bernardomg.association.settings.domain.model;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public record PublicSettings(String map, String calendar, String email, String instagram) {

    public PublicSettings(final String map, final String calendar, final String email, final String instagram) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(calendar);
        Objects.requireNonNull(email);
        Objects.requireNonNull(instagram);

        this.map = StringUtils.trim(map);
        this.calendar = StringUtils.trim(calendar);
        this.email = StringUtils.trim(email);
        this.instagram = StringUtils.trim(instagram);
    }

}
