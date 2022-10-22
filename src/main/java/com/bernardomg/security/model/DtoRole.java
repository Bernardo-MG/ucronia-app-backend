
package com.bernardomg.security.model;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoRole implements Role {

    @NotNull
    private Long                  id;

    @NotNull
    private String                name;

    @NotNull
    private Collection<Privilege> privileges;

}
