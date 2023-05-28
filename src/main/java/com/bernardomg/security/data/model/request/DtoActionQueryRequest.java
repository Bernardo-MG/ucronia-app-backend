
package com.bernardomg.security.data.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoActionQueryRequest implements ActionQueryRequest {

    private String name;

}
