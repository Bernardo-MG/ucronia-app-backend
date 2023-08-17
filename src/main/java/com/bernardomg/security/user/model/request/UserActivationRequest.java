
package com.bernardomg.security.user.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * Password change during password recovery request.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Data
public class UserActivationRequest {

    /**
     * The new password.
     */
    @NotEmpty
    private String password;

}
