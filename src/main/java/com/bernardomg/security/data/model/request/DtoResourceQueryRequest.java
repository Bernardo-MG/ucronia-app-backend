
package com.bernardomg.security.data.model.request;

import com.bernardomg.security.data.model.Resource;

import lombok.Data;

@Data
public final class DtoResourceQueryRequest implements Resource {

    private Long   id;

    private String name;

}
