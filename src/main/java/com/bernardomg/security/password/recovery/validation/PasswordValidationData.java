
package com.bernardomg.security.password.recovery.validation;

import java.util.Optional;

import com.bernardomg.security.user.persistence.model.PersistentUser;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class PasswordValidationData {

    private final String                   email;

    private final Optional<PersistentUser> user;

}
