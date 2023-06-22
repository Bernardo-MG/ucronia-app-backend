
package com.bernardomg.security.user.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DtoRoleCreateRequest implements RoleCreateRequest {

    @NotNull
    private String name;

}
