
package com.bernardomg.security.user.model.request;

import com.bernardomg.security.user.model.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoRoleCreateRequest implements Role {

    private final Long id = null;

    @NotNull
    private String     name;

}
