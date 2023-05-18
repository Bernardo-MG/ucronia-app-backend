
package com.bernardomg.security.data.controller.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoPermissionForm {

    @NotNull
    private Long actionId;

    @NotNull
    private Long resourceId;

}
