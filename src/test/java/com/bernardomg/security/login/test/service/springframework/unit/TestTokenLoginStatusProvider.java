
package com.bernardomg.security.login.test.service.springframework.unit;

import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.TokenLoginStatus;
import com.bernardomg.security.login.service.LoginStatusProvider;
import com.bernardomg.security.login.service.TokenLoginStatusProvider;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.TokenEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("TokenLoginStatusProvider - get status")
public class TestTokenLoginStatusProvider {

    @Mock
    private TokenEncoder<String> tokenEncoder;

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

    private final LoginStatusProvider getLoginStatusProvider() {
        given(tokenEncoder.encode(ArgumentMatchers.anyString())).willReturn(TokenConstants.TOKEN);

        return new TokenLoginStatusProvider(tokenEncoder);
    }

}
