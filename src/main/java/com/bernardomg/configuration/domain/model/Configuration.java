
package com.bernardomg.configuration.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public final class Configuration {

    private String key;

    private String value;

}
