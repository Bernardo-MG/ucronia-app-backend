
package com.bernardomg.security.permission.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoPermission implements Permission {

    private final String action;

    private final Long   actionId;

    private final String resource;

    private final Long   resourceId;

}
