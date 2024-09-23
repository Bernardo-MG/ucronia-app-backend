
package com.bernardomg.settings.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public final class Setting {

    private String code;

    private String type;

    private String value;

}
