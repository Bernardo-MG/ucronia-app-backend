
package com.bernardomg.security.data.controller.model;

import com.bernardomg.security.data.model.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoCreateRoleForm implements Role {

    private final Long id = null;

    @NotNull
    private String     name;

}
