
package com.bernardomg.security.data.controller.model;

import javax.validation.constraints.NotNull;

import com.bernardomg.security.data.model.Role;

import lombok.Data;

@Data
public final class DtoUpdateRoleForm implements Role {

    @NotNull
    private Long   id;

    @NotNull
    private String name;

}
