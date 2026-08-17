
package com.bernardomg.association.profile.domain.model;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public record ContactChannel(ContactMethod contactMethod, String detail) {

    public ContactChannel(final ContactMethod contactMethod, final String detail) {
        Objects.requireNonNull(contactMethod, "Contact method can't be null");
        Objects.requireNonNull(detail, "Detail can't be null");

        this.contactMethod = contactMethod;
        this.detail = StringUtils.trim(detail);
    }

}
