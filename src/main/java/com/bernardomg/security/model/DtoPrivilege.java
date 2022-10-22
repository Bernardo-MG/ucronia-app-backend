
package com.bernardomg.security.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoPrivilege implements Privilege {

    @NotNull
    private Long   id;

    @NotNull
    private String name;

}
