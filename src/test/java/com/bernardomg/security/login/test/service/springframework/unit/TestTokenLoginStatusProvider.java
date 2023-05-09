
package com.bernardomg.security.login.test.service.springframework.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.TokenLoginStatus;
import com.bernardomg.security.login.service.LoginStatusProvider;
import com.bernardomg.security.login.service.TokenLoginStatusProvider;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.provider.TokenProvider;

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

        Assertions.assertInstanceOf(TokenLoginStatus.class, status);

        Assertions.assertTrue(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
        Assertions.assertEquals(TokenConstants.TOKEN, ((TokenLoginStatus) status).getToken());
    }

    @Test
    @DisplayName("Returns a default login status when the user is logged")
    public void testGetStatus_NotLogged() {
        final LoginStatus status;

        status = getLoginStatusProvider().getStatus("admin", false);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    private final LoginStatusProvider getLoginStatusProvider() {
        final TokenProvider tokenProvider;

        tokenProvider = Mockito.mock(TokenProvider.class);
        Mockito.when(tokenProvider.generateToken(ArgumentMatchers.anyString()))
            .thenReturn(TokenConstants.TOKEN);

        return new TokenLoginStatusProvider(tokenProvider);
    }

}
