/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.bernardomg.security.user.service.UserService;
import com.bernardomg.security.user.test.config.ValidUser;
import com.bernardomg.security.user.token.api.model.UserTokenStatus;
import com.bernardomg.security.user.token.test.config.annotation.UserRegisteredConsumedToken;
import com.bernardomg.security.user.token.test.config.annotation.UserRegisteredExpiredToken;
import com.bernardomg.security.user.token.test.config.annotation.UserRegisteredToken;
import com.bernardomg.security.user.token.test.config.constant.TokenConstants;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Role service - token validation")
class ITUserServiceToken {

    @Autowired
    private UserService service;

    public ITUserServiceToken() {
        super();
        // TODO: Maybe should be a unit test
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("A consumed token is not valid")
    @ValidUser
    @UserRegisteredConsumedToken
    void testValidateToken_Consumed() {
        final UserTokenStatus status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status.isValid())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("An expired token is not valid")
    @ValidUser
    @UserRegisteredExpiredToken
    void testValidateToken_Expired() {
        final UserTokenStatus status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status.isValid())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("A valid token is valid")
    @ValidUser
    @UserRegisteredToken
    void testValidateToken_Valid() {
        final UserTokenStatus status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status.isValid())
            .isTrue();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

}
