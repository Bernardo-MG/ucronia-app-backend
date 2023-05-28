
package com.bernardomg.security.data.model.request;

import com.bernardomg.security.data.model.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoRoleCreationRequest implements Role {

    private final Long id = null;

    @NotNull
    private String     name;

}
