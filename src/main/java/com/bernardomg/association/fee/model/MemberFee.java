
package com.bernardomg.association.fee.model;

import java.time.LocalDateTime;

public interface MemberFee {

    public LocalDateTime getDate();

    public Long getId();

    public Long getMemberId();

    public String getMemberName();

    public Boolean getPaid();

}
