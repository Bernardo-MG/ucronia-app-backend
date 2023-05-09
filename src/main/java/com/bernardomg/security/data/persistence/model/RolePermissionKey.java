
package com.bernardomg.security.data.persistence.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class RolePermissionKey implements Serializable {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -7233957066746780621L;

    private Long              actionId;

    private Long              resourceId;

    private Long              roleId;

}
