
package com.bernardomg.security.user.model.request;

import com.bernardomg.constraint.Email;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ValidatedUserUpdate implements UserUpdate {

    /**
     * User expired flag.
     */
    @NotNull
    private Boolean credentialsExpired;

    /**
     * User email.
     */
    @NotNull
    @Email
    private String  email;

    /**
     * User enabled flag.
     */
    @NotNull
    private Boolean enabled;

    /**
     * User expired flag.
     */
    @NotNull
    private Boolean expired;

    /**
     * User id.
     */
    @NotNull
    private Long    id;

    /**
     * User locked flag.
     */
    @NotNull
    private Boolean locked;

    /**
     * User name.
     */
    private String  name;

    /**
     * User username.
     */
    private String  username;

}
