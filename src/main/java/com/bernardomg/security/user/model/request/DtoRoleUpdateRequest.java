
package com.bernardomg.security.user.model.request;

import com.bernardomg.security.user.model.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoRoleUpdateRequest implements Role {

    @NotNull
    private Long   id;

    @NotNull
    private String name;

}
