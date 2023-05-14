
package com.bernardomg.security.data.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoId {

    @NotNull
    private Long id;

}
