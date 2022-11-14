
package com.bernardomg.security.password.recovery.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoPasswordRecovery implements PasswordRecovery {

    @NotNull
    private String email;

}
