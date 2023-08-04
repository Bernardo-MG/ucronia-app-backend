
package com.bernardomg.security.password.reset.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * Password change during password recovery request.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Data
public class PasswordResetChangeRequest {

    /**
     * The new password.
     */
    @NotEmpty
    private String password;

}
