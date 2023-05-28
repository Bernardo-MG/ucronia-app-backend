
package com.bernardomg.security.data.model.request;

import com.bernardomg.security.data.model.Action;

import lombok.Data;

@Data
public final class DtoActionQueryRequest implements Action {

    private Long   id;

    private String name;

}
