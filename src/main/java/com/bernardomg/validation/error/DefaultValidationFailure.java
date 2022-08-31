
package com.bernardomg.validation.error;

import lombok.Data;

@Data
public final class DefaultValidationFailure implements ValidationFailure {

    private String error;

}
