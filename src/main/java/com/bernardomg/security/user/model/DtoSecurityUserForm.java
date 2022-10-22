
package com.bernardomg.security.user.model;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoSecurityUserForm implements SecurityUserForm {

    @NotNull
    private Calendar date;

    private Long     id;

    @NotNull
    private Long     memberId;

    @NotNull
    private Boolean  paid;

}
