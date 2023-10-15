
package com.bernardomg.security.user.token.api.model;

import java.time.LocalDateTime;

public interface UserToken {

    public LocalDateTime getCreationDate();

    public LocalDateTime getExpirationDate();

    public Long getId();

    public String getScope();

    public String getToken();

    public String getUsername();

    public boolean isConsumed();

    public boolean isRevoked();

}
