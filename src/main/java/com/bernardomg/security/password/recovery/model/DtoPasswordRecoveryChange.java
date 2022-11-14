
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

    private String currentPassword;

    private String password;

    private String token;

}
