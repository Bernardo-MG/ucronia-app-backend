
package com.bernardomg.security.user.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DtoUserUpdateRequest implements UserUpdateRequest {

    /**
     * User expired flag.
     */
    @NotNull
    private Boolean credentialsExpired;

    /**
     * User email.
     */
    @NotNull
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
