
package com.bernardomg.security.controller.form;

import javax.validation.constraints.NotNull;

import com.bernardomg.security.model.Role;

import lombok.Data;

@Data
public final class DtoCreateRoleForm implements Role {

    private final Long id = null;

    @NotNull
    private String     name;

}
