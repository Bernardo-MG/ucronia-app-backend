
package com.bernardomg.security.data.controller.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoPermissionForm {

    @NotNull
    private Long action;

    @NotNull
    private Long resource;

}
