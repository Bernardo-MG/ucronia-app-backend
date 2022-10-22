
package com.bernardomg.security.user.model;

import java.util.Calendar;

public interface SecurityUserForm {

    public Calendar getDate();

    public Long getId();

    public Long getMemberId();

    public Boolean getPaid();

}
