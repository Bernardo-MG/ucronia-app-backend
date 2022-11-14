
package com.bernardomg.security.password.recovery.model;

import lombok.Data;

/**
 * DTO implementation of {@link PasswordRecoveryChange}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Data
public class DtoPasswordRecoveryChange implements PasswordRecoveryChange {

    /**
     * The current user password. Used to authenticate change.
     */
    private String currentPassword;

    /**
     * The new password.
     */
    private String password;

    /**
     * The password change token. Used to authenticate change.
     */
    private String token;

}
