
package com.bernardomg.security.user.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ValidatedActionQuery implements ActionQuery {

    private String name;

}
