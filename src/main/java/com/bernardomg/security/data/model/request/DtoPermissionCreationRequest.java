
package com.bernardomg.security.data.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoPermissionCreationRequest {

    @NotNull
    private Long actionId;

    @NotNull
    private Long resourceId;

}
