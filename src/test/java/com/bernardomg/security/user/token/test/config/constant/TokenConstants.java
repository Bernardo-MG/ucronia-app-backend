
package com.bernardomg.security.user.token.test.config.constant;

import java.nio.charset.Charset;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

public final class TokenConstants {

    public static final SecretKey KEY   = Keys.hmacShaKeyFor(
        "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
            .getBytes(Charset.forName("UTF-8")));

    public static final String    SCOPE = "scope";

    public static final String    TOKEN = "bd656aaf-0c18-4178-bcdf-71ccb7f320fa";

    private TokenConstants() {
        super();
    }

}
