
package com.bernardomg.security.profile.model;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import com.bernardomg.security.permission.model.SecurityPermission;

import lombok.Data;

@Data
public final class DtoSecurityProfile implements SecurityProfile {

    @NotNull
    private Long                           id;

    @NotNull
    private String                         name;

    @NotNull
    private Collection<SecurityPermission> permissions;

}
