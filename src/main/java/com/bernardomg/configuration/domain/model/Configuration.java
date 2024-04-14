
package com.bernardomg.configuration.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public final class Configuration {

    private String code;

    private String type;

    private String value;

}
