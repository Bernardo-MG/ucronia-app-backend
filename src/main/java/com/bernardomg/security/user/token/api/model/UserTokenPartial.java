
package com.bernardomg.security.user.token.api.model;

import java.time.LocalDateTime;

public interface UserTokenPartial {

    public Boolean getConsumed();

    public LocalDateTime getExpirationDate();

}
