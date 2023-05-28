
package com.bernardomg.security.data.model.request;

import com.bernardomg.security.data.model.Role;

import lombok.Data;

@Data
public final class DtoRoleQueryRequest implements Role {

    private Long   id;

    private String name;

}
