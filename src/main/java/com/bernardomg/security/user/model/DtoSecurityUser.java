
package com.bernardomg.security.user.model;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import com.bernardomg.security.profile.model.SecurityProfile;

import lombok.Data;

@Data
public final class DtoSecurityUser implements SecurityUser {

    @NotNull
    private Boolean                     enabled;

    private Long                        id;

    @NotNull
    private Boolean                     locked;

    @NotNull
    private Long                        memberId;

    @NotNull
    private Collection<SecurityProfile> profiles;

    @NotNull
    private String                      username;

}
