
package com.bernardomg.security.profile.model;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoSecurityProfileForm implements SecurityProfileForm {

    @NotNull
    private Calendar date;

    private Long     id;

    @NotNull
    private Long     memberId;

    @NotNull
    private Boolean  paid;

}
