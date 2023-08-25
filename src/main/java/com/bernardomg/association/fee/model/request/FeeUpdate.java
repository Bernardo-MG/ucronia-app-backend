
package com.bernardomg.association.fee.model.request;

import java.time.LocalDateTime;

public interface FeeUpdate {

    public LocalDateTime getDate();

    public Long getMemberId();

    public Boolean getPaid();

}
