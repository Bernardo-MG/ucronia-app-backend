
package com.bernardomg.security.data.model.request;

import com.bernardomg.security.data.model.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoRoleUpdateRequest implements Role {

    @NotNull
    private Long   id;

    @NotNull
    private String name;

}
