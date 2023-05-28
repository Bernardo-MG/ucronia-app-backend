
package com.bernardomg.security.data.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoResourceQueryRequest implements ResourceQueryRequest {

    private String name;

}
