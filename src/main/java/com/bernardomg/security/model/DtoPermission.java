
package com.bernardomg.security.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoPermission implements Permission {

    @NotNull
    private Long   id;

    @NotNull
    private String name;

}
