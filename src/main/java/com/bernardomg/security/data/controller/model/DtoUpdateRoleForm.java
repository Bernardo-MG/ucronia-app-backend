
package com.bernardomg.security.data.controller.model;

import com.bernardomg.security.data.model.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoUpdateRoleForm implements Role {

    @NotNull
    private Long   id;

    @NotNull
    private String name;

}
