
package com.bernardomg.security.password.reset.model.request;

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
    private String password;

    /**
     * The password change token. Used to authenticate change.
     */
    private String token;

}
