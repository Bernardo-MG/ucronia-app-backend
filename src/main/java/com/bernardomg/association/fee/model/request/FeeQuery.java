
package com.bernardomg.association.fee.model.request;

import java.time.LocalDateTime;

public interface FeeQuery {

    public LocalDateTime getDate();

    public LocalDateTime getEndDate();

    public LocalDateTime getStartDate();

}
