
package com.bernardomg.validation;

import lombok.Data;

@Data
public final class DefaultValidationError implements ValidationError {

    private String error;

}
