
package com.bernardomg.association.library.book.domain.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Title(String supertitle, String title, String subtitle) {

    public Title(final String supertitle, final String title, final String subtitle) {
        this.supertitle = StringUtils.trim(supertitle);
        this.title = StringUtils.trim(title);
        this.subtitle = StringUtils.trim(subtitle);
    }

    @JsonProperty("fullTitle")
    public final String fullTitle() {
        return String.format("%s %s %s", supertitle, title, subtitle)
            .trim();
    }

}
