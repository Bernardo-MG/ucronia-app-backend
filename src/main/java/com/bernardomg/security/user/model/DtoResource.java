
package com.bernardomg.security.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoResource implements Resource {

    private final Long   id;

    private final String name;

}