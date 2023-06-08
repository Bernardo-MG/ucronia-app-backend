
package com.bernardomg.security.login.test.service.springframework.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.TokenLoginStatus;
import com.bernardomg.security.login.service.LoginStatusProvider;
import com.bernardomg.security.login.service.TokenLoginStatusProvider;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.TokenEncoder;

@DisplayName("TokenLoginStatusProvider - get status")
public class TestTokenLoginStatusProvider {

    public TestTokenLoginStatusProvider() {
        super();
    }

    @Test
    @DisplayName("Returns a token login status when the user is logged")
    public void testGetStatus_Logged() {
        final LoginStatus status;

        status = getLoginStatusProvider().getStatus("admin", true);

        Assertions.assertThat(status)
            .isInstanceOf(TokenLoginStatus.class);

        Assertions.assertThat(status.getLogged())
            .isTrue();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
        Assertions.assertThat(((TokenLoginStatus) status).getToken())
            .isEqualTo(TokenConstants.TOKEN);
    }

    @Test
    @DisplayName("Returns a default login status when the user is logged")
    public void testGetStatus_NotLogged() {
        final LoginStatus status;

        status = getLoginStatusProvider().getStatus("admin", false);

        Assertions.assertThat(status)
            .isNotInstanceOf(TokenLoginStatus.class);

        Assertions.assertThat(status.getLogged())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @SuppressWarnings("unchecked")
    private final LoginStatusProvider getLoginStatusProvider() {
        final TokenEncoder<String> tokenEncoder;

        tokenEncoder = Mockito.mock(TokenEncoder.class);
        Mockito.when(tokenEncoder.encode(ArgumentMatchers.anyString()))
            .thenReturn(TokenConstants.TOKEN);

        return new TokenLoginStatusProvider(tokenEncoder);
    }

}
