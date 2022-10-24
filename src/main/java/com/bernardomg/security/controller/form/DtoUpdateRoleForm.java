
package com.bernardomg.security.controller.form;

import javax.validation.constraints.NotNull;

import com.bernardomg.security.model.Role;

import lombok.Data;

@Data
public final class DtoUpdateRoleForm implements Role {

    @NotNull
    private Long   id;

    @NotNull
    private String name;

}
