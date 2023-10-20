
package com.bernardomg.security.user.token.api.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UserTokenPatchRequest implements UserTokenPartial {

    private Boolean       consumed;

    private LocalDateTime expirationDate;

    private Boolean       revoked;

}
