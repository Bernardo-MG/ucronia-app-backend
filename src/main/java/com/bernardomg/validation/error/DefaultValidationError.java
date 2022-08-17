
package com.bernardomg.validation.error;

import lombok.Data;

@Data
public final class DefaultValidationError implements ValidationError {

    private String error;

}
